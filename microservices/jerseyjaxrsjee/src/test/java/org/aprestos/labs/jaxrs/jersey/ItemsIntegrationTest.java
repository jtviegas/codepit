package org.aprestos.labs.jaxrs.jersey;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ItemsIntegrationTest {

  private Client client;

  private WebTarget target;

  private TrustManager[] getTrustManager() {
    return new TrustManager[] { new X509TrustManager() {
      @Override
      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }

      @Override
      public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
      }

      @Override
      public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
      }
    } };
  }

  @Before
  public void setUp() throws Exception {

    SSLContext sslContext = null;
    try {
      sslContext = SSLContext.getInstance("SSL");
      // Create a new X509TrustManager
      sslContext.init(null, getTrustManager(), null);
    } catch (NoSuchAlgorithmException | KeyManagementException e) {
      throw e;
    }

    this.client = ClientBuilder.newBuilder().hostnameVerifier((s, session) -> true).sslContext(sslContext).build();

  }

  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test to see that the message "Got it!" is sent in the response.
   */
  @Test
  public void testGetIt() {

    URI builder = UriBuilder.fromPath("jerseyjaxrsjee/api").scheme("https").host("localhost").port(9443).build();
    this.target = this.client.target(builder);
    assertEquals(Status.OK.getStatusCode(), this.target.path("items").request().get().getStatus());

  }
}
