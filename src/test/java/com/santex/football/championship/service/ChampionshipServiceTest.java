package com.santex.football.championship.service;

import com.santex.football.championship.client.FootballClient;
import com.santex.football.championship.client.model.*;
import com.santex.football.championship.model.Competition;
import com.santex.football.championship.model.Team;
import com.santex.football.championship.repository.ChampionshipRepository;
import com.santex.football.championship.repository.TeamRepository;
import com.santex.football.championship.service.exception.LeagueAlreadyImportedException;
import com.santex.football.championship.service.exception.LeagueNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ChampionshipServiceTest {

  private static String EXISTING_LEAGUE = "EF";
  private static String LEAGUE_TO_IMPORT = "CL";
  private static String COUNT_CODE = "HL";
  private static String COUNT_CODE_NOT_EXISTING = "HF";
  private static String PLAYER_NAME = "pname";
  private static String TEAM_NAME = "tname";
  private static String TEAM_NAME_NOT_IN_DB = "notInDBTeam";
  private static Integer TEAM_ID_TO_CLIENT = 1;
  private static Long TEAM_ID = 2l;

  @Mock
  private ChampionshipRepository championshipRepository;
  @Mock
  private TeamRepository teamRepository;
  @Mock
  private FootballClient footballClient;
  @Mock
  private Team team;
  @Mock
  private LeagueResponse leagueResponse;
  @Mock
  private Competition competition;
  @Mock
  private CompetitionResponse competitionResponse;
  @Mock
  private TeamResponse teamResponse;
  @Mock
  private TeamResponse teamResponseInDB;
  @Mock
  private SquadResponse squadResponse;
  @Mock
  private PlayerResponse playerResponse;
  @Mock
  private AreaResponse areaResponse;

  @Rule
  public ExpectedException ex = ExpectedException.none();

  private ChampionshipService championshipService;

  @Before
  public void setUp(){
    championshipService = new ChampionshipService(footballClient,championshipRepository,teamRepository);

    when(squadResponse.getPlayers()).thenReturn(Arrays.asList(playerResponse));
    when(squadResponse.getId()).thenReturn(TEAM_ID_TO_CLIENT);

    when(championshipRepository.existsByCode(eq(EXISTING_LEAGUE)))
      .thenReturn(Boolean.TRUE);
    when(championshipRepository.existsByCode(eq(LEAGUE_TO_IMPORT)))
      .thenReturn(Boolean.FALSE);
    when(footballClient.obtainLeagueByCode(eq(LEAGUE_TO_IMPORT)))
      .thenReturn(leagueResponse);
    when(footballClient.obtainMembersOfTeam(eq(TEAM_ID_TO_CLIENT)))
      .thenReturn(squadResponse);

    when(team.getName()).thenReturn(TEAM_NAME);
    when(teamResponseInDB.getName()).thenReturn(TEAM_NAME);
    when(teamResponseInDB.getId()).thenReturn(TEAM_ID.intValue());
    when(teamResponse.getName()).thenReturn(TEAM_NAME_NOT_IN_DB);
    when(teamResponse.getId()).thenReturn(TEAM_ID_TO_CLIENT);
    when(playerResponse.getName()).thenReturn(PLAYER_NAME);

    when(competitionResponse.getArea()).thenReturn(areaResponse);

    when(leagueResponse.getTeams()).thenReturn(Arrays.asList(teamResponse,teamResponseInDB));
    when(leagueResponse.getCompetitionResponse()).thenReturn(competitionResponse);

    when(teamRepository.findByNameIn(Arrays.asList(TEAM_NAME_NOT_IN_DB,TEAM_NAME)))
      .thenReturn(Arrays.asList(team));

    when(championshipRepository.countPlayers(COUNT_CODE)).thenReturn(3);
    when(championshipRepository.existsByCode(COUNT_CODE)).thenReturn(Boolean.TRUE);
    when(championshipRepository.existsByCode(COUNT_CODE_NOT_EXISTING)).thenReturn(Boolean.FALSE);
    when(championshipRepository.findByCode(COUNT_CODE)).thenReturn(Optional.of(competition));
    when(championshipRepository.findByCode(COUNT_CODE_NOT_EXISTING)).thenReturn(Optional.empty());
  }

  @Test
  public void testImportTeam(){
    championshipService.importLeague(LEAGUE_TO_IMPORT);
    verify(teamRepository).findByNameIn(Arrays.asList(TEAM_NAME_NOT_IN_DB,TEAM_NAME));
    verify(footballClient).obtainMembersOfTeam(eq(TEAM_ID_TO_CLIENT));
    verify(footballClient).obtainLeagueByCode(eq(LEAGUE_TO_IMPORT));
    verify(championshipRepository).save(anyObject());
  }

  @Test
  public void testImportThrowsException(){
    ex.expect(LeagueAlreadyImportedException.class);
    ex.expectMessage("League already imported");
    championshipService.importLeague(EXISTING_LEAGUE);
    verify(teamRepository,never()).findByNameIn(anyList());
    verify(footballClient,never()).obtainMembersOfTeam(anyInt());
    verify(footballClient,never()).obtainLeagueByCode(anyString());
  }

  @Test
  public void testCountPlayers(){
    assertThat(championshipService.countPlayers(COUNT_CODE),is(3));
    verify(championshipRepository).countPlayers(eq(COUNT_CODE));
  }

  @Test
  public void testCountPlayersThrowsError(){
    ex.expect(LeagueNotFoundException.class);
    ex.expectMessage("League not found");
    championshipService.countPlayers(COUNT_CODE_NOT_EXISTING);
    verify(championshipRepository).countPlayers(eq(COUNT_CODE_NOT_EXISTING));
  }

  @Test
  public void testFindByCode(){
    assertThat(championshipService.findByCode(COUNT_CODE),is(competition));
    verify(championshipRepository).findByCode(eq(COUNT_CODE));
  }

  @Test
  public void testFindByCodeNotFound(){
    ex.expect(LeagueNotFoundException.class);
    ex.expectMessage("League not found");
    championshipService.findByCode(COUNT_CODE_NOT_EXISTING);
    verify(championshipRepository,never()).findByCode(eq(COUNT_CODE_NOT_EXISTING));
  }

}
