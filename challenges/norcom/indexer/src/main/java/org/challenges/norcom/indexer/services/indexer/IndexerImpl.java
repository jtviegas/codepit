package org.challenges.norcom.indexer.services.indexer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class IndexerImpl implements Indexer {

	private static final Logger logger = LoggerFactory.getLogger(IndexerImpl.class);

	@Value("${org.challenges.norcom.indexer.url}")
	private String url;

	private RestTemplate client;
	private HttpHeaders headers;

	public IndexerImpl() {
	}

	@Override
	public void index(Path folder) {
		logger.trace("[index] in - folder: {}", folder);
		for (final File entry : folder.toFile().listFiles()) {
			bulkPost(Paths.get(entry.getAbsolutePath()));
		}
		logger.trace("[index] out");
	}

	private void bulkPost(Path file) {
		logger.trace("[bulkPost] in - file: {}", file);

		PathResource resource = new PathResource(file);
		logger.info("[bulkPost] indexing onto url: {} file: {} ", url, file.getFileName());
		ResponseEntity<Void> response = getClient().exchange(url, HttpMethod.POST,
				new HttpEntity<>(resource, getHeaders()), new ParameterizedTypeReference<Void>() {
				});

		if (HttpStatus.OK.equals(response.getStatusCode()))
			logger.info("[bulkPost] indexing is complete");
		else
			logger.warn("[bulkPost] indexing was not successful: status code {}", response.getStatusCode());

		logger.trace("[bulkPost] out");
	}

	private HttpHeaders getHeaders() {
		if (null == headers) {
			headers = new HttpHeaders();
			headers.set("Accept", "application/json");
			headers.setContentType(MediaType.APPLICATION_JSON);
		}
		return headers;
	}

	private RestTemplate getClient() {
		if (null == client) {
			SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
			requestFactory.setBufferRequestBody(false);
			client = new RestTemplate(requestFactory);
			MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
			jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			client.getMessageConverters().add(jsonHttpMessageConverter);
			client.getMessageConverters().add(new ResourceHttpMessageConverter(true));
			client.getMessageConverters().add(new ByteArrayHttpMessageConverter());
			client.getMessageConverters().add(new StringHttpMessageConverter());
			client.getMessageConverters().add(new AllEncompassingFormHttpMessageConverter());
		}
		return client;
	}
}
