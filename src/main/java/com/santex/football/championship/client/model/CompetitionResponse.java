package com.santex.football.championship.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CompetitionResponse {

  @JsonProperty("id")
  private Integer id;
  @JsonProperty("name")
  private String name;
  @JsonProperty("code")
  private String code;
  @JsonProperty("area")
  private AreaResponse area;
}
