package com.santex.football.championship.client.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TeamResponseTest {

  private static Integer ID = 1;
  private static String NAME = "name";
  private static String EMAIL = "email";
  private static String TLA = "tla";
  private static String SHORT_NAME = "short name";

  private TeamResponse teamResponse;

  @Before
  public void setUp(){
    teamResponse = new TeamResponse();
    teamResponse.setId(ID);
    teamResponse.setName(NAME);
    teamResponse.setShortName(SHORT_NAME);
    teamResponse.setEmail(EMAIL);
    teamResponse.setTla(TLA);
  }

  @Test
  public void testAccessors(){
    assertThat(teamResponse.getId(),is(ID));
    assertThat(teamResponse.getName(),is(NAME));
    assertThat(teamResponse.getShortName(),is(SHORT_NAME));
    assertThat(teamResponse.getTla(),is(TLA));
    assertThat(teamResponse.getEmail(),is(EMAIL));
  }


}
