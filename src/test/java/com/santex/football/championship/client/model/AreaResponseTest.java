package com.santex.football.championship.client.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AreaResponseTest {

  private static final String NAME = "name";

  private AreaResponse areaResponse;

  @Before
  public void setUp(){
    areaResponse = new AreaResponse();
    areaResponse.setName(NAME);
  }

  @Test
  public void testAccessors(){
    assertThat(areaResponse.getName(),is(NAME));
  }
}
