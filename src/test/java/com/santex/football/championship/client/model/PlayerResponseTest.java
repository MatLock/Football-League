package com.santex.football.championship.client.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PlayerResponseTest {

  private static final String NAME = "name";
  private static final String NATIONALITY = "Arg";
  private static final String POSITION = "pos";
  private static final String ROLE = "role";
  private static final String COUNTRY = "ARG";
  private static final Integer ID = 1;
  private static Date BIRTH = Date.from(Instant.now());

  private PlayerResponse player;

  @Before
  public void setUp(){
    player = new PlayerResponse();
    player.setId(ID);
    player.setName(NAME);
    player.setNationality(NATIONALITY);
    player.setCountryOfBirth(COUNTRY);
    player.setPosition(POSITION);
    player.setRole(ROLE);
    player.setDateOfBirth(BIRTH);
  }

  @Test
  public void testAccessors(){
    assertThat(player.getCountryOfBirth(),is(COUNTRY));
    assertThat(player.getId(),is(ID));
    assertThat(player.getName(),is(NAME));
    assertThat(player.getNationality(),is(NATIONALITY));
    assertThat(player.getDateOfBirth(),is(BIRTH));
    assertThat(player.getRole(),is(ROLE));
    assertThat(player.getPosition(),is(POSITION));
  }

}
