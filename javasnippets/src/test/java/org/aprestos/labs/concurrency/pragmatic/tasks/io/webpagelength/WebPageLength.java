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

public class WebPageLength implements Task {

	private static final List<String> sites = Arrays.asList("https://news.google.com", "https://www.amazon.co.uk/",
		      "https://www.theguardian.com/international", "https://www.thetimes.co.uk/", "https://www.ft.com",
		      "https://www1.folha.uol.com.br/internacional/en/", "https://elpais.com/", "http://www.lavanguardia.com",
		      "http://www.elmundo.es/", "https://www.lequipe.fr/");
	
	private static final Logger logger = LoggerFactory.getLogger(WebPageLength.class);
	private static final Random random = new Random();
	
	private final RestClient client;
	

	public WebPageLength() {
		client = new RestClient();
	}

	@Override
	public void run(Map<String, Object> context) {
		logger.debug("[run|in] {}", context.toString());
		try {

			ResponseEntity<String> r = client.getResponse(new ParameterizedTypeReference<String>() {
			}, getSite(), null, (Object) null);
			
			int length=r.getBody().length();
			if (context.containsKey("length"))
				length += (Integer)context.get("length");
			context.put("length", length);
			
			int count = 1;
			if (context.containsKey("count"))
				count += (Integer)context.get("count");
			context.put("count", count);

		} finally {
			logger.debug("[run|out] {}", context.toString());
		}
	}
	
	private String getSite() {
		 return sites.get(random.nextInt(sites.size()));
	}

}
