package com.santex.football.championship.client.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SquadResponseTest {

  private static String NAME = "name";
  private static String SHORT_NAME = "short name";
  private static Integer ID = 1;

  @Mock
  private PlayerResponse playerResponse;

  private SquadResponse squadResponse;

  @Before
  public void setUp(){
    squadResponse = new SquadResponse();
    squadResponse.setId(ID);
    squadResponse.setName(NAME);
    squadResponse.setShortName(SHORT_NAME);
    squadResponse.setPlayers(Arrays.asList(playerResponse));
  }

  @Test
  public void testAccessors(){
    assertThat(squadResponse.getId(),is(ID));
    assertThat(squadResponse.getName(),is(NAME));
    assertThat(squadResponse.getShortName(),is(SHORT_NAME));
    assertThat(squadResponse.getPlayers().get(0),is(playerResponse));
  }

}
