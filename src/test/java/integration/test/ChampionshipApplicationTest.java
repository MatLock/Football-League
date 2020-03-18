package integration.test;

import com.google.gson.Gson;
import com.santex.football.championship.ChampionshipApplication;
import com.santex.football.championship.controller.response.BasicResponse;
import com.santex.football.championship.controller.response.TotalPlayersResponse;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = ChampionshipApplication.class
)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChampionshipApplicationTest {

  private static final String CODE = "CL";

  @Autowired
  private MockMvc mockMvc;
  private Gson gson;

  @Before
  public void setUp(){
    gson = new Gson();
  }

  @Test
  @SneakyThrows
  public void testAImportLeague(){
    MvcResult result = importLeague(CODE);
    assertThat(result.getResponse().getStatus(),is(HttpStatus.CREATED.value()));
    assertThat(result.getResponse().getContentAsString(),is(notNullValue()));
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
    assertThat(totalPlayersResponse.getResponse(),is(243));
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
