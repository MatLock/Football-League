package com.santex.football.championship.client;

import com.santex.football.championship.client.exception.ClientException;
import com.santex.football.championship.client.exception.ClientNotFoundException;
import com.santex.football.championship.client.model.LeagueResponse;
import com.santex.football.championship.client.model.SquadResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
@Slf4j
public class FootballClient {

  private static final String COMPETITIONS = "/competitions/";
  private static final String TEAMS = "/teams/";
  private static final String AUTH_HEADER = "X-Auth-Token";
  private static final String AUTH_HEADER_VALUE = "cc8d9203e0c7457f88ca894780449817";
  private static final String APPLICATION_JSON = "application/json";

  private String footballUrl;

  @Autowired
  public FootballClient(@Value("${championship.football_url}") String footballUrl){
    this.footballUrl = footballUrl;
  }

  public LeagueResponse obtainLeagueByCode(String code){
    try{
      String uri = buildURI(COMPETITIONS,code,TEAMS);
      ResponseEntity<LeagueResponse> entity = createRestTemplate().exchange(uri, HttpMethod.GET,createDefaultHttpEntity(), LeagueResponse.class);
      if(entity.getStatusCode().equals(HttpStatus.NOT_FOUND)){
        throw new ClientNotFoundException();
      }
      return entity.getBody();
    }catch (RestClientException e){
      throw new ClientException(e.getMessage());
    }
  }

  public SquadResponse obtainMembersOfTeam(Integer teamId){
    try{
      String uri = buildURI(TEAMS,String.valueOf(teamId));
      ResponseEntity<SquadResponse> entity = createRestTemplate().exchange(uri, HttpMethod.GET,createDefaultHttpEntity(),SquadResponse.class);
      if(entity.getStatusCode().equals(HttpStatus.NOT_FOUND)){
        throw new ClientNotFoundException();
      }
      return entity.getBody();
    }catch (RestClientException e){
      throw new ClientException(e.getMessage());
    }
  }

  protected RestTemplate createRestTemplate(){
    return new RestTemplate();
  }

  private HttpEntity<Void> createDefaultHttpEntity(){
    return new HttpEntity<Void>(null,createHeaders());
  }

  private String buildURI(String... paths){
    UriComponentsBuilder builder =UriComponentsBuilder.fromUriString(footballUrl);
    for (String p : paths){
      builder.path(p);
    }
    return builder.buildAndExpand()
                  .toString();
  }

  private HttpHeaders createHeaders(){
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_TYPE,APPLICATION_JSON);
    headers.set(AUTH_HEADER,AUTH_HEADER_VALUE);
    return headers;
  }


}
