package com.santex.football.championship.client.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LeagueResponseTest {

  @Mock
  private TeamResponse teamResponse;
  @Mock
  private CompetitionResponse competitionResponse;

  private LeagueResponse leagueResponse;

  @Before
  public void setUp(){
    leagueResponse = new LeagueResponse();
    leagueResponse.setCompetitionResponse(competitionResponse);
    leagueResponse.setTeams(Arrays.asList(teamResponse));
  }

  @Test
  public void testAccessors(){
    assertThat(leagueResponse.getCompetitionResponse(),is(competitionResponse));
    assertThat(leagueResponse.getTeams().size(),is(1));
  }

}
