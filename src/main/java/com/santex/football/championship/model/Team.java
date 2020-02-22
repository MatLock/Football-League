package com.santex.football.championship.model;

import com.santex.football.championship.client.model.SquadResponse;
import com.santex.football.championship.client.model.TeamResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity(name = "TEAM")
public class Team {

  @Id
  @Column(name = "ID",unique = true)
  private Integer id;
  @Column(name = "NAME")
  private String name;
  @Column(name = "SHORT_NAME")
  private String shortName;
  @Column(name = "EMAIL")
  private String email;
  @Column(name = "TLA")
  private String tla;
  @OneToMany(cascade= CascadeType.ALL)
  @JoinColumn(name="TEAM_ID")
  private Set<Player> players;

  public Team(TeamResponse teamResponse, SquadResponse squadResponse){
    this.id = teamResponse.getId();
    this.name = teamResponse.getName();
    this.shortName = teamResponse.getShortName();
    this.tla = teamResponse.getTla();
    this.email = teamResponse.getEmail();
    if(squadResponse != null){
      this.players = squadResponse.getPlayers()
          .stream()
          .map(Player::new)
          .collect(Collectors.toSet());
    }
  }

}
