package com.santex.football.championship.client.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CompetitionResponseTest {

  private static final Integer ID = 2;
  private static final String NAME = "name";
  private static final String CODE = "code";

  @Mock
  private AreaResponse areaResponse;

  private CompetitionResponse competitionResponse;

  @Before
  public void setUp(){
    competitionResponse = new CompetitionResponse();
    competitionResponse.setArea(areaResponse);
    competitionResponse.setId(ID);
    competitionResponse.setName(NAME);
    competitionResponse.setCode(CODE);
  }

  @Test
  public void testAccessors(){
    assertThat(competitionResponse.getArea(),is(areaResponse));
    assertThat(competitionResponse.getCode(),is(CODE));
    assertThat(competitionResponse.getName(),is(NAME));
    assertThat(competitionResponse.getId(),is(ID));
  }

}
