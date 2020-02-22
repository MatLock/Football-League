package com.santex.football.championship.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SquadResponse {

  @JsonProperty("id")
  private Integer id;
  @JsonProperty("name")
  private String name;
  @JsonProperty("shortName")
  private String shortName;
  @JsonProperty("squad")
  private List<PlayerResponse> players;

}
