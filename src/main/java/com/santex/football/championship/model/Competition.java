package com.santex.football.championship.model;

import com.santex.football.championship.client.model.CompetitionResponse;
import com.santex.football.championship.client.model.LeagueResponse;
import com.santex.football.championship.client.model.SquadResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity(name = "COMPETITION")
public class Competition {

  @Id
  @Column(name = "ID",unique = true)
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  @Column(name = "NAME")
  private String name;
  @Column(name = "AREA_NAME")
  private String areaName;
  @Column(name = "CODE")
  private String code;
  @ManyToMany(cascade = { CascadeType.ALL })
  @JoinTable(
    name = "COMPETITION_TEAM",
    joinColumns = { @JoinColumn(name = "COMPETITION_ID") },
    inverseJoinColumns = { @JoinColumn(name = "TEAM_ID") })
  private Set<Team> teams;


  public Competition(LeagueResponse leagueResponse, List<SquadResponse> squadResponse){
    CompetitionResponse competitionResponse = leagueResponse.getCompetitionResponse();
    this.name = competitionResponse.getName();
    this.areaName = competitionResponse.getArea().getName();
    this.code = competitionResponse.getCode();
    this.teams = leagueResponse.getTeams()
      .stream()
      .map(teamResponse ->{
        Optional<SquadResponse> squadOpt = squadResponse.stream().filter(sq -> sq.getId().equals(teamResponse.getId())).findFirst();
        if(squadOpt.isPresent()){
          return new Team(teamResponse,squadOpt.get());
        }
        return new Team(teamResponse,null);
      } ).collect(Collectors.toSet());
  }

}
