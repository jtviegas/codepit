package org.aprestos.labs.concurrency.pragmatic.tasks.io.webpagelength;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.aprestos.labs.concurrency.pragmatic.tasks.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

public class GetPageLength implements Task {

  private static final List<String> sites = Arrays.asList("https://news.google.com", "https://www.amazon.co.uk/",
      "https://www.theguardian.com/international", "https://www.thetimes.co.uk/", "https://www.ft.com",
      "https://www1.folha.uol.com.br/internacional/en/", "https://elpais.com/", "http://www.lavanguardia.com",
      "http://www.elmundo.es/", "https://www.lequipe.fr/");

  private static final Logger logger = LoggerFactory.getLogger(GetPageLength.class);

  private static final Random random = new Random();

  private final RestClient client;

  public GetPageLength() {
    client = new RestClient();
  }

  @Override
  public void run(Map<String, Object> context) {
    logger.debug("[run|in] {}", context.toString());
    try {

      ResponseEntity<String> r = client.getResponse(new ParameterizedTypeReference<String>() {
      }, getSite(), null, (Object) null);

      int length = r.getBody().length();
      context.put("outcome", length);

    } finally {
      logger.debug("[run|out] {}", context.toString());
    }
  }

  private String getSite() {
    return sites.get(random.nextInt(sites.size()));
  }

}
