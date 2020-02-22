package com.santex.football.championship.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueResponse {

  @JsonProperty("teams")
  private List<TeamResponse> teams;
  @JsonProperty("competition")
  private CompetitionResponse competitionResponse;
}
