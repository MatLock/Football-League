package com.santex.football.championship.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TotalPlayersResponse extends BasicResponse{

  private Integer total;

  public TotalPlayersResponse(Integer total,Boolean error, String message){
    super(message, error);
    this.total = total;
  }

}
