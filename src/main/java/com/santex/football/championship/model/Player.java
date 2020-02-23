package com.santex.football.championship.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.santex.football.championship.client.model.PlayerResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity(name = "PLAYER")
public class Player {

  @Id
  @Column(name = "ID",unique = true)
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  @Column(name = "NAME")
  private String name;
  @Column(name = "POSITION")
  private String position;
  @Column(name = "COUNTRY_OF_BIRTH")
  private String countryOfBirth;
  @Column(name = "DATE_OF_BIRTH")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date dateOfBirth;
  @Column(name = "NATIONALITY")
  private String nationality;
  @Column(name = "ROLE")
  private String role;

  public Player(PlayerResponse playerResponse){
    this.name = playerResponse.getName();
    this.countryOfBirth = playerResponse.getCountryOfBirth();
    this.nationality = playerResponse.getNationality();
    this.position = playerResponse.getPosition();
    this.role = playerResponse.getRole();
    this.dateOfBirth = playerResponse.getDateOfBirth();
  }


}
