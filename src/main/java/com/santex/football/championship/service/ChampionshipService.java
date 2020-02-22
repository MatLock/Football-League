package com.santex.football.championship.service;

import com.santex.football.championship.client.FootballClient;
import com.santex.football.championship.client.model.LeagueResponse;
import com.santex.football.championship.client.model.SquadResponse;
import com.santex.football.championship.model.Competition;
import com.santex.football.championship.repository.ChampionshipRepository;
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

  @Autowired
  public ChampionshipService(FootballClient footballClient,ChampionshipRepository championshipRepository){
    this.footballClient = footballClient;
    this.championshipRepository = championshipRepository;
  }

  //Limiting request to football API due limitations of free tier subscription
  @Transactional
  @SneakyThrows
  public void importLeague(String leagueCode){
    if(championshipRepository.existsByCode(leagueCode)){
      throw new LeagueAlreadyImportedException("League already imported");
    }
    LeagueResponse leagueResponse = footballClient.obtainLeagueByCode(leagueCode);
    List<SquadResponse> squads = leagueResponse.getTeams()
                         .subList(0,8)
                         .parallelStream()
                         .map(team -> footballClient.obtainMembersOfTeam(team.getId()))
                         .collect(Collectors.toList());

    Competition competition = createCompetition(leagueResponse,squads);
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


}
