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

  private static final int    STRING_NOT_FOUND      = -1;
  private static final String API_PERSONS           = "http://localhost:9081/api/persons/";
  private static final String PERSON_KEY            = "2884685b-b262-41e2-93a7-669d6be25243";
  private static final String NO_CONTENT_PERSON_KEY = "669d6be25243";

  private CloseableHttpClient httpClient;

  @Before
  public void setUp() {
    this.httpClient = HttpClients.createDefault();
  }

  /**
   * That create with a valid entity works.
   *
   * @throws ClientProtocolException
   * @throws IOException
   */
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
    assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED);
  }

  public void thatCreateTenWorks() throws ClientProtocolException, IOException {
    // given
    final HttpPost request = new HttpPost(API_PERSONS);
    request.setEntity(
        new StringEntity(
            "{\"name\":\"Norma\",\"birthDay\":" + Calendar.getInstance().getTimeInMillis() + ",\"age\":28}",
            ContentType.create("application/json", "UTF-8")));

    // when
    final CloseableHttpResponse response = httpClient.execute(request);

    // then
    assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED);
  }

  @Test
  public void thatCreateWithEmptyEntityReturnBadRequestWorks() throws ClientProtocolException, IOException {
    // given
    final HttpPost request = new HttpPost(API_PERSONS);
    request.setEntity(new StringEntity("", ContentType.create("application/json", "UTF-8")));

    // when
    final CloseableHttpResponse response = httpClient.execute(request);

    // then
    assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  public void thatRetrieveReturnOkWorks() throws ClientProtocolException, IOException {
    // given
    final HttpGet request = new HttpGet(API_PERSONS + PERSON_KEY);

    // when
    final CloseableHttpResponse response = httpClient.execute(request);

    // then
    assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
  }

  @Test
  public void thatRetrieveReturnNotEmptyEntityWorks() throws ClientProtocolException, IOException {
    // given
    final HttpGet request = new HttpGet(API_PERSONS + PERSON_KEY);

    // when
    final CloseableHttpResponse response = httpClient.execute(request);

    // then
    assertTrue(EntityUtils.toString(response.getEntity()).indexOf(PERSON_KEY) != STRING_NOT_FOUND);
  }

  @Test
  public void thatRetrieveReturnNoContentWorks() throws ClientProtocolException, IOException {
    // given
    final HttpGet request = new HttpGet(API_PERSONS + NO_CONTENT_PERSON_KEY);

    // when
    final CloseableHttpResponse response = httpClient.execute(request);

    // then
    assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT);
  }

  @Test
  public void thatFindAllReturnOkWorks() throws ClientProtocolException, IOException {
    // given
    final HttpGet request = new HttpGet(API_PERSONS);

    // when
    final CloseableHttpResponse response = httpClient.execute(request);

    // then
    assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
  }

}
