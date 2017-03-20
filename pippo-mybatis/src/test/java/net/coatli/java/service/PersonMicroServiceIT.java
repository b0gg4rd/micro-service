package net.coatli.java.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Calendar;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

public class PersonMicroServiceIT {

  private static final int STRING_NOT_FOUND = -1;

  private static final String API_PERSONS = "http://localhost:8338/api/persons/";

  private static final String PERSON_KEY = "2884685b-b262-41e2-93a7-669d6be25243";

  private CloseableHttpClient httpClient;

  @Before
  public void setUp() {
    this.httpClient = HttpClients.createDefault();
  }

  @Test
  public void thatCreateWorks() throws ClientProtocolException, IOException {
    // given
    final HttpPost request = new HttpPost(API_PERSONS);
    request.setEntity(
        new StringEntity(
            "{\"name\":\"Norma\",\"birthDay\":" + Calendar.getInstance().getTimeInMillis() + ",\"age\":28}",
            ContentType.create("application/json", "UTF-8")));

    // when
    final CloseableHttpResponse response = httpClient.execute(request);

    // then
    try {
      assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED);
    } finally {
      response.close();
    }
  }

  @Test
  public void thatRetrieveReturnOkWorks() throws ClientProtocolException, IOException {
    // given
    final HttpGet request = new HttpGet(API_PERSONS + PERSON_KEY);

    // when
    final CloseableHttpResponse response = httpClient.execute(request);

    // then
    try {
      assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
    } finally {
      response.close();
    }
  }

  @Test
  public void thatRetrieveReturnNotEmptyEntityWorks() throws ClientProtocolException, IOException {
 // given
    final HttpGet request = new HttpGet(API_PERSONS + PERSON_KEY);

    // when
    final CloseableHttpResponse response = httpClient.execute(request);

    // then
    try {
      assertTrue(EntityUtils.toString(response.getEntity()).indexOf(PERSON_KEY) != STRING_NOT_FOUND);
    } finally {
      response.close();
    }
  }

  @Test
  public void thatFindAllReturnOKWorks()
      throws ClientProtocolException, IOException {
    // given
    final HttpGet request = new HttpGet(API_PERSONS);

    // when
    final CloseableHttpResponse response = httpClient.execute(request);

    // then
    try {
      assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
    } finally {
      response.close();
    }
  }

}
