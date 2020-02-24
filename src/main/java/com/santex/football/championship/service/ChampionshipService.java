package com.santex.football.championship.service;

import com.santex.football.championship.client.FootballClient;
import com.santex.football.championship.client.model.LeagueResponse;
import com.santex.football.championship.client.model.SquadResponse;
import com.santex.football.championship.client.model.TeamResponse;
import com.santex.football.championship.model.Competition;
import com.santex.football.championship.model.Team;
import com.santex.football.championship.repository.ChampionshipRepository;
import com.santex.football.championship.repository.TeamRepository;
import com.santex.football.championship.service.exception.LeagueAlreadyImportedException;
import com.santex.football.championship.service.exception.LeagueNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChampionshipService {

  private FootballClient footballClient;
  private ChampionshipRepository championshipRepository;
  private TeamRepository teamRepository;

  @Autowired
  public ChampionshipService(FootballClient footballClient, ChampionshipRepository championshipRepository, TeamRepository teamRepository){
    this.footballClient = footballClient;
    this.championshipRepository = championshipRepository;
    this.teamRepository = teamRepository;
  }

  //Limiting request to football API due limitations of free tier subscription
  @Transactional
  @SneakyThrows
  public void importLeague(String leagueCode){
    if(championshipRepository.existsByCode(leagueCode)){
      throw new LeagueAlreadyImportedException("League already imported");
    }
    LeagueResponse leagueResponse = footballClient.obtainLeagueByCode(leagueCode);
    List<Team> teamsSaved = teamRepository.findByNameIn(teamNamesOf(leagueResponse.getTeams()));
    List<SquadResponse> squads = obtainSquads(leagueResponse,teamsSaved);
    Competition competition = createCompetition(leagueResponse,squads);
    competition.addTeams(teamsSaved);
    championshipRepository.save(competition);
  }

  public Integer countPlayers(String code){
    if(! championshipRepository.existsByCode(code)){
      throw new LeagueNotFoundException("League not found");
    }
    return championshipRepository.countPlayers(code);
  }

  public Competition findByCode(String code){
    Optional<Competition>competition = championshipRepository.findByCode(code);
    if(competition.isPresent()){
      return competition.get();
    }
    throw new LeagueNotFoundException("League not found");
  }

  private Competition createCompetition(LeagueResponse leagueResponse,List<SquadResponse> squadResponse){
    return new Competition(leagueResponse,squadResponse);
  }

  private List<SquadResponse> obtainSquads(LeagueResponse leagueResponse,List<Team>teamsSaved){
    List<TeamResponse> teamsNotSaved = obtainTeamsNotSaved(leagueResponse.getTeams(),teamsSaved);
    return obtainSquadsFromApi(teamsNotSaved);
  }

  private List<TeamResponse> obtainTeamsNotSaved(List<TeamResponse> teams, List<Team> teamsSaved){
    return teams.stream().filter(tr -> {
      return !teamsSaved.stream().anyMatch( ts -> ts.getName().equalsIgnoreCase(tr.getName()));
    }).collect(Collectors.toList());
  }

  private List<SquadResponse> obtainSquadsFromApi(List<TeamResponse> teams){
    List<TeamResponse> toFind = teams.size() > 8 ? teams.subList(0,8) : teams;
    return toFind.parallelStream()
                 .map(team -> footballClient.obtainMembersOfTeam(team.getId()))
                 .collect(Collectors.toList());
  }

  private List<String> teamNamesOf(List<TeamResponse> teamResponses){
    return teamResponses.stream()
      .map(t -> t.getName())
      .collect(Collectors.toList());
  }


}
