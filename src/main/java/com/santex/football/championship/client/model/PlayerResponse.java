package com.santex.football.championship.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PlayerResponse {

  @JsonProperty("id")
  private Integer id;
  @JsonProperty("name")
  private String name;
  @JsonProperty("position")
  private String position;
  @JsonProperty("role")
  private String role;
  @JsonProperty("nationality")
  private String nationality;
  @JsonProperty("countryOfBirth")
  private String countryOfBirth;
  @JsonProperty("dateOfBirth")
  private Date dateOfBirth;
}
