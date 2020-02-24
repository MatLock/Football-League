package com.santex.football.championship.controller.advice;

import com.santex.football.championship.client.exception.ClientException;
import com.santex.football.championship.client.exception.ClientNotFoundException;
import com.santex.football.championship.controller.response.BasicResponse;
import com.santex.football.championship.service.exception.LeagueAlreadyImportedException;
import com.santex.football.championship.service.exception.LeagueNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionHandlerControllerTest {

  private static final String MESSAGE = "message";

  @Mock
  private ClientException clientException;
  @Mock
  private LeagueAlreadyImportedException leagueAlreadyImportedException;
  @Mock
  private LeagueNotFoundException leagueNotFoundException;
  @InjectMocks
  private ExceptionHandlerController exceptionHandlerController;

  @Before
  public void setUp(){
    when(clientException.getMessage()).thenReturn(MESSAGE);
    when(leagueAlreadyImportedException.getMessage()).thenReturn(MESSAGE);
  }

  @Test
  public void testHandleClientException(){
    ResponseEntity<BasicResponse> response = exceptionHandlerController.handleReservationBadRequestError(clientException);
    assertOnResponseEntity(response,HttpStatus.GATEWAY_TIMEOUT,MESSAGE);
  }

  @Test
  public void testHandlerServiceException(){
    ResponseEntity<BasicResponse> response = exceptionHandlerController.handlerServiceException(leagueAlreadyImportedException);
    assertOnResponseEntity(response,HttpStatus.CONFLICT,MESSAGE);
  }

  @Test
  public void testHandleReservationBadRequestError(){
    ResponseEntity<BasicResponse> response = exceptionHandlerController.handleNotFoundException(leagueNotFoundException);
    assertOnResponseEntity(response,HttpStatus.NOT_FOUND,"Not Found");
  }


  private void assertOnResponseEntity(ResponseEntity<BasicResponse> response,HttpStatus status,String msg){
    assertThat(response,is(notNullValue()));
    assertThat(response.getStatusCode(),is(status));
    assertThat(response.getBody().getMessage(),is(msg));
    assertThat(response.getBody().getError(),is(Boolean.TRUE));
  }
}
