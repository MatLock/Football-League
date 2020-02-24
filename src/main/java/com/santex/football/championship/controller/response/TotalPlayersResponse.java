package com.santex.football.championship.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TotalPlayersResponse extends BasicResponse{

  private Integer response;

  public TotalPlayersResponse(Integer total,Boolean error, String message){
    super(message, error);
    this.response = total;
  }

}
