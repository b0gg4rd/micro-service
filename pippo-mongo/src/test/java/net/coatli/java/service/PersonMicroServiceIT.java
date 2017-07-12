package net.coatli.java.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Calendar;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

public class PersonMicroServiceIT {

  private static final String API_PERSONS = "http://localhost:9081/api/persons/";

  private CloseableHttpClient httpClient;

  @Before
  public void setUp() {
    this.httpClient = HttpClients.createDefault();
  }

  /**
   * Create with valid entity works.
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
    try {
      assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED);
    } finally {
      response.close();
    }
  }

  @Test
  public void thatCreateWithBadEntityReturnBadRequestWorks() throws ClientProtocolException, IOException {
    // given
    final HttpPost request = new HttpPost(API_PERSONS);
    request.setEntity(new StringEntity("", ContentType.create("application/json", "UTF-8")));

    // when
    final CloseableHttpResponse response = httpClient.execute(request);

    // then
    try {
      assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST);
    } finally {
      response.close();
    }
  }

  public void thatRetrieveWorks() throws ClientProtocolException, IOException {
 // given
    final HttpPost request = new HttpPost(API_PERSONS);
    request.setEntity(new StringEntity("", ContentType.create("application/json", "UTF-8")));

    // when
    final CloseableHttpResponse response = httpClient.execute(request);

    // then
    try {
      assertTrue(response.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST);
    } finally {
      response.close();
    }
  }

}
