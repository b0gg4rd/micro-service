package net.coatli.java.service;

import org.junit.Before;

public class PersonMicroServiceIT {

  private static final int    STRING_NOT_FOUND      = -1;
  private static final String API_PERSONS           = "http://localhost:9081/api/persons/";
  private static final String PERSON_KEY            = "2884685b-b262-41e2-93a7-669d6be25243";
  private static final String NO_CONTENT_PERSON_KEY = "669d6be25243";

  @Before
  public void setUp() {
  }

}
