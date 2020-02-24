package com.santex.football.championship.controller.advice;

import com.santex.football.championship.client.exception.ClientException;
import com.santex.football.championship.client.exception.ClientNotFoundException;
import com.santex.football.championship.controller.response.BasicResponse;
import com.santex.football.championship.service.exception.LeagueAlreadyImportedException;
import com.santex.football.championship.service.exception.LeagueNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ClientException.class})
  public ResponseEntity<BasicResponse> handleReservationBadRequestError(ClientException e){
    BasicResponse response = new BasicResponse(e.getMessage(),Boolean.TRUE);
    return new ResponseEntity<>(response, HttpStatus.GATEWAY_TIMEOUT);
  }

  @ExceptionHandler({LeagueAlreadyImportedException.class})
  public ResponseEntity<BasicResponse> handlerServiceException(LeagueAlreadyImportedException e){
    BasicResponse response = new BasicResponse(e.getMessage(),Boolean.TRUE);
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

  @ExceptionHandler({ClientNotFoundException.class, LeagueNotFoundException.class})
  public ResponseEntity<BasicResponse> handleNotFoundException(Exception e){
    BasicResponse response = new BasicResponse("Not Found",Boolean.TRUE);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

}
