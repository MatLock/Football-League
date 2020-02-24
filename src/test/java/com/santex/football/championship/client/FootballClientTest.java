package com.santex.football.championship.client;

import com.santex.football.championship.client.exception.ClientException;
import com.santex.football.championship.client.exception.ClientNotFoundException;
import com.santex.football.championship.client.model.LeagueResponse;
import com.santex.football.championship.client.model.SquadResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class FootballClientTest {

  private static final String GET_TEAMS = "https://api.football-data.org/v2/competitions/CL/teams/";
  private static final String GET_MEMBERS = "https://api.football-data.org/v2/teams/2";
  private static final String CODE = "CL";
  private static final String MESSAGE = "ERROR";
  private static final Integer TEAM_ID = 2;

  @Mock
  private RestTemplate restTemplate;
  @Mock
  private ResponseEntity<LeagueResponse> leagueResponseEntity;
  @Mock
  private ResponseEntity<SquadResponse> squadResponseEntity;
  @Mock
  private LeagueResponse leagueResponse;
  @Mock
  private SquadResponse squadResponse;
  @Mock
  private RestClientException restClientException;

  @InjectMocks
  @Spy
  private FootballClient footballClient;

  @Rule
  public ExpectedException ex = ExpectedException.none();

  @Before
  public void setUp(){
    when(footballClient.createRestTemplate()).thenReturn(restTemplate);

    when(restTemplate.exchange(eq(GET_TEAMS),eq(HttpMethod.GET),any(),eq(LeagueResponse.class)))
      .thenReturn(leagueResponseEntity);
    when(restTemplate.exchange(eq(GET_MEMBERS),eq(HttpMethod.GET),any(),eq(SquadResponse.class)))
      .thenReturn(squadResponseEntity);

    when(squadResponseEntity.getBody()).thenReturn(squadResponse);
    when(squadResponseEntity.getStatusCode()).thenReturn(HttpStatus.OK);

    when(leagueResponseEntity.getBody()).thenReturn(leagueResponse);
    when(leagueResponseEntity.getStatusCode()).thenReturn(HttpStatus.OK);

    when(restClientException.getMessage()).thenReturn(MESSAGE);
  }

  @Test
  public void testObtainLeagueByCode(){
    assertThat(footballClient.obtainLeagueByCode(CODE),is(leagueResponse));
    verify(leagueResponseEntity).getStatusCode();
    verify(leagueResponseEntity).getBody();
    verify(restTemplate).exchange(anyString(),any(),any(),eq(LeagueResponse.class));
  }

  @Test
  public void testObtainLeagueByCodeThrowsNotFound(){
    when(leagueResponseEntity.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
    ex.expect(ClientNotFoundException.class);
    footballClient.obtainLeagueByCode(CODE);
    verify(leagueResponseEntity).getStatusCode();
    verify(leagueResponseEntity,never()).getBody();
  }

  @Test
  public void testObtainLeagueByCodeThrowsClientError(){
   ex.expect(ClientException.class);
   ex.expectMessage(MESSAGE);
   when(restTemplate.exchange(eq(GET_TEAMS),eq(HttpMethod.GET),any(),eq(LeagueResponse.class)))
     .thenThrow(restClientException);
   footballClient.obtainLeagueByCode(CODE);
   verify(leagueResponseEntity,never()).getStatusCode();
   verify(leagueResponseEntity,never()).getBody();
  }

  @Test
  public void obtainMembersOfTeam(){
    assertThat(footballClient.obtainMembersOfTeam(TEAM_ID),is(squadResponse));
    verify(squadResponseEntity).getStatusCode();
    verify(squadResponseEntity).getBody();
    verify(restTemplate).exchange(anyString(),any(),any(),eq(SquadResponse.class));
  }

  @Test
  public void obtainMembersOfTeamThrowsError(){
    when(restTemplate.exchange(eq(GET_MEMBERS),eq(HttpMethod.GET),any(),eq(SquadResponse.class)))
      .thenThrow(restClientException);
    ex.expect(ClientException.class);
    ex.expectMessage(MESSAGE);
    footballClient.obtainMembersOfTeam(TEAM_ID);
    verify(squadResponseEntity,never()).getStatusCode();
    verify(squadResponseEntity,never()).getBody();
  }

  @Test
  public void obtainMembersOfTeamReturnsNotFound(){
    ex.expect(ClientNotFoundException.class);
    when(squadResponseEntity.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
    footballClient.obtainMembersOfTeam(TEAM_ID);
    verify(squadResponseEntity).getStatusCode();
    verify(squadResponseEntity,never()).getBody();
  }

}
