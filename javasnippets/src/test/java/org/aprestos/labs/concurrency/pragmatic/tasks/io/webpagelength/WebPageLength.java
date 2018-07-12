package org.aprestos.labs.concurrency.pragmatic.tasks.io.webpagelength;

import org.aprestos.labs.concurrency.pragmatic.tasks.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

public class WebPageLength implements Task {

  private static final Logger logger = LoggerFactory.getLogger(WebPageLength.class);

  private final RestClient client;

  private final String site;

  @Override
  public int run() {
    int result = 0;
    logger.debug("[run|in] {}", site);
    try {
      ResponseEntity<String> r = client.getResponse(new ParameterizedTypeReference<String>() {
      }, site, null, (Object) null);

      result = r.getBody().length();

    } finally {
      logger.debug("[run|out] {}", result);
    }
    return result;
  }

  public WebPageLength(String site) {
    client = new RestClient();
    this.site = site;
  }

}
