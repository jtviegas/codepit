package org.aprestos.labs.concurrency.pragmatic.assorted;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class RestClient {

  private RestTemplate restTemplate;

  public RestClient() {

    try {

      SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
          new SSLContextBuilder().loadTrustMaterial(null, new TrustAllStrategy()).build());
      HttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier())
          .setSSLSocketFactory(socketFactory).build();

      ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
      restTemplate = new RestTemplate(requestFactory);

    } catch (Exception e) {
      throw new RuntimeException("could not load client", e);
    }
  }

  public <T> ResponseEntity<T> getResponse(ParameterizedTypeReference<T> type, String uri,
      MultiValueMap<String, String> queryParams, Object... uriParams) {

    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri);
    if (null != queryParams && !queryParams.isEmpty())
      uriBuilder.queryParams(queryParams);

    MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
    header.add("Content-Type", "application/json");

    UriComponents u = null != uriParams ? uriBuilder.buildAndExpand(uriParams) : uriBuilder.build();
    return restTemplate.exchange(u.encode().toUri(), HttpMethod.GET, new HttpEntity<Object>(header), type);
  }

}
