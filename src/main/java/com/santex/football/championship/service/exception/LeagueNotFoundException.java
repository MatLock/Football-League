package com.santex.football.championship.service.exception;

public class LeagueNotFoundException extends  RuntimeException {

  public LeagueNotFoundException(String msg){
    super(msg);
  }
}
