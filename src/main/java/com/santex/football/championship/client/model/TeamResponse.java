package com.santex.football.championship.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamResponse {

  @JsonProperty("id")
  private Integer id;
  @JsonProperty("name")
  private String name;
  @JsonProperty("shortName")
  private String shortName;
  @JsonProperty("email")
  private String email;
  @JsonProperty("tla")
  private String tla;

}
