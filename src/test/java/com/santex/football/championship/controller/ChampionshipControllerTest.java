package com.santex.football.championship.controller;

import com.santex.football.championship.controller.response.CompetitionResponse;
import com.santex.football.championship.controller.response.TotalPlayersResponse;
import com.santex.football.championship.model.Competition;
import com.santex.football.championship.service.ChampionshipService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class ChampionshipControllerTest {

  private static final Integer TOTAL_PLAYERS = 3;
  private static final String CODE = "CL";

  @Mock
  private ChampionshipService championshipService;
  @Mock
  private Competition competition;

  @InjectMocks
  private ChampionshipController championshipController;

  @Before
  public void setUp(){
    when(championshipService.countPlayers(eq(CODE))).thenReturn(TOTAL_PLAYERS);
    when(championshipService.findByCode(eq(CODE))).thenReturn(competition);
  }

  @Test
  public void testImportTeam(){
    championshipController.importTeam(CODE);
    verify(championshipService).importLeague(eq(CODE));
  }

  @Test
  public void testCountPlayers(){
    TotalPlayersResponse totalPlayersResponse = championshipController.getTotalPlayers(CODE);
    assertThat(totalPlayersResponse.getMessage(),is(nullValue()));
    assertThat(totalPlayersResponse.getError(),is(Boolean.FALSE));
    assertThat(totalPlayersResponse.getResponse(),is(TOTAL_PLAYERS));
    verify(championshipService).countPlayers(CODE);
  }

  @Test
  public void testGetLeague(){
    CompetitionResponse response = championshipController.findByCode(CODE);
    assertThat(response.getMessage(),is(nullValue()));
    assertThat(response.getError(),is(Boolean.FALSE));
    assertThat(response.getResponse(),is(competition));
    verify(championshipService).findByCode(eq(CODE));
  }

}
