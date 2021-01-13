package integration.test;

import com.google.gson.Gson;
import com.santex.football.championship.ChampionshipApplication;
import com.santex.football.championship.controller.response.BasicResponse;
import com.santex.football.championship.controller.response.TotalPlayersResponse;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = ChampionshipApplication.class
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChampionshipApplicationTest {

  private static final String CODE = "CL";
  private static final String TEAMS_RESPONSE = "src/test/resources/json/teams.json";
  private static final String TEAM_MEMBERS = "src/test/resources/json/team_4.json";

  @Autowired
  private MockMvc mockMvc;
  private ClientAndServer mockServer;
  private Gson gson;

  @Before
  public void setUp(){
    mockServer = startClientAndServer(8089);
    gson = new Gson();
    respondToImportLeague();
  }

  @After
  public void tearDown(){
    mockServer.stop();
  }

  @SneakyThrows
  private void respondToImportLeague(){
    mockServer.when(
            request()
                    .withMethod(HttpMethod.GET.name())
                    .withPath("/competitions/CL/teams/")
                    .withHeader("Content-type", "application/json")
                    .withHeader("X-Auth-Token","cc8d9203e0c7457f88ca894780449817")
            ).respond(
                    response()
                            .withStatusCode(200)
                            .withHeader("Content-Type", "application/json; charset=utf-8")
                            .withBody(FileUtils.readFileToString(new File(TEAMS_RESPONSE)))

            );

    mockServer.when(
            request()
                    .withMethod(HttpMethod.GET.name())
                    .withPath("/teams/4")
                    .withHeader("Content-type", "application/json")
                    .withHeader("X-Auth-Token","cc8d9203e0c7457f88ca894780449817")
    ).respond(
            response()
                    .withStatusCode(200)
                    .withHeader("Content-Type", "application/json; charset=utf-8")
                    .withBody(FileUtils.readFileToString(new File(TEAM_MEMBERS)))

    );
  }

  @Test
  @SneakyThrows
  public void testAImportLeague() {
    MvcResult result = importLeague(CODE);
    assertThat(result.getResponse().getStatus(),is(HttpStatus.CREATED.value()));
    assertThat(result.getResponse().getContentAsString(),is(notNullValue()));
    mockServer.verify(
            request()
              .withMethod(HttpMethod.GET.name())
              .withPath("/competitions/CL/teams/")
              .withHeader("Content-type", "application/json")
              .withHeader("X-Auth-Token","cc8d9203e0c7457f88ca894780449817")
    );
    mockServer.verify(
            request()
                    .withMethod(HttpMethod.GET.name())
                    .withPath("/teams/4")
                    .withHeader("Content-type", "application/json")
                    .withHeader("X-Auth-Token","cc8d9203e0c7457f88ca894780449817")
    );
  }

  @Test
  @SneakyThrows
  public void testBImportLeagueImported(){
    MvcResult result = importLeague(CODE);
    BasicResponse response = gson.fromJson(result.getResponse().getContentAsString(),BasicResponse.class);
    assertThat(result.getResponse().getStatus(),is(HttpStatus.CONFLICT.value()));
    assertThat(response.getError(),is(Boolean.TRUE));
    assertThat(response.getMessage(),is("League already imported"));
  }

  @Test
  @SneakyThrows
  public void testCcountPlayers(){
    MvcResult mvcResult = countPlayers(CODE);
    TotalPlayersResponse totalPlayersResponse = gson.fromJson(mvcResult.getResponse().getContentAsString(),TotalPlayersResponse.class);
    assertThat(mvcResult.getResponse().getStatus(),is(HttpStatus.OK.value()));
    assertThat(totalPlayersResponse.getError(),is(Boolean.FALSE));
    assertThat(totalPlayersResponse.getMessage(),is(nullValue()));
    assertThat(totalPlayersResponse.getResponse(),is(32));
  }

  @SneakyThrows
  private MvcResult countPlayers(String code){
    return mockMvc.perform(MockMvcRequestBuilders.get(format("/total-players/%s",code))
      .accept(MediaType.APPLICATION_JSON))
      .andReturn();
  }

  @SneakyThrows
  private MvcResult importLeague(String code){
    return mockMvc.perform(MockMvcRequestBuilders.get(format("/import-league/%s",code))
      .accept(MediaType.APPLICATION_JSON))
      .andReturn();
  }

}
