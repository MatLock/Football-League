package com.santex.football.championship.controller.response;

import com.santex.football.championship.model.Competition;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CompetitionResponse extends BasicResponse{

  private Competition response;

  public CompetitionResponse(Competition competition,Boolean error,String message){
    super(message,error);
    response = competition;
  }
}
