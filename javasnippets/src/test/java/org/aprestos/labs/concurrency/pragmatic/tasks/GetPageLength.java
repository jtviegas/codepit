package org.aprestos.labs.concurrency.pragmatic.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class GetPageLength implements Task {

  private static final Logger logger = LoggerFactory.getLogger(GetPageLength.class);

  private Map<String, Object> context;

  private final String id;

  private static final RestClient client = new RestClient();

  public static List<Callable<Void>> createTasks(final int iterations, final Map<String, Object> context) {
    logger.trace("[createTasks|in] {}", iterations);

    final List<String> sites = Arrays.asList("https://news.google.com", "https://www.amazon.co.uk/",
        "https://www.theguardian.com/international", "https://www.thetimes.co.uk/", "https://www.ft.com",
        "https://www1.folha.uol.com.br/internacional/en/", "https://elpais.com/", "http://www.lavanguardia.com",
        "http://www.elmundo.es/", "https://www.lequipe.fr/");

    final List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();

    for (int i = 0; i < iterations; i++) {
      final Task task = new GetPageLength();
      context.put(task.getId(), Pair.of(sites.get(i % sites.size()), 0));
      task.setContext(context);
      tasks.add(task);
    }

    logger.trace("[createTasks|out] tasks: {}", tasks.toString());
    return tasks;
  }

  public GetPageLength() {
    id = Integer.toString(idGenerator.getAndIncrement());
  }

  @Override
  public Void call() throws Exception {
    logger.trace("[call|in]");
    try {
      @SuppressWarnings("unchecked")
      Pair<String, Integer> input = (Pair<String, Integer>) context.get(this.id);
      ResponseEntity<String> r = client.getResponse(new ParameterizedTypeReference<String>() {
      }, input.getLeft(), null, (Object) null);

      context.put(id, Pair.of(input.getLeft(), r.getBody().length()));
      logger.debug("[call] page length of [{}]: {}", new Object[] { input.getLeft(), r.getBody().length() });
    } catch (Exception x) {
      logger.debug("[call] ooppss ", x);
    } finally {
      logger.trace("[call|out]");
    }
    return null;
  }

  @Override
  public void setContext(Map<String, Object> context) {
    this.context = context;
  }

  @Override
  public String getId() {
    return id;
  }

  private static class RestClient {

    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);

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
      logger.trace("[getResponse|in] {}", uri);

      UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri);
      if (null != queryParams && !queryParams.isEmpty())
        uriBuilder.queryParams(queryParams);

      MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
      header.add("Content-Type", "application/json");

      UriComponents u = null != uriParams ? uriBuilder.buildAndExpand(uriParams) : uriBuilder.build();
      logger.trace("[getResponse|out] {}");
      return restTemplate.exchange(u.encode().toUri(), HttpMethod.GET, new HttpEntity<Object>(header), type);
    }

  }

}
