package org.challenges.norcom.indexer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.SerializationFeature;

public class Indexer {
	private static final Logger logger = LoggerFactory.getLogger(Indexer.class);
	/* private RestClient client; */

	public Indexer() {
		/* this.client = restClient; */

		/*
		 * RestHighLevelClient client = new RestHighLevelClient( RestClient.builder( new
		 * HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9201,
		 * "http")));
		 */

	}

	public void handle(Path f, String url) {

		try {

			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", "application/json");
			headers.setContentType(MediaType.APPLICATION_JSON);

			SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
			requestFactory.setBufferRequestBody(false);

			final RestTemplate restTemplate = new RestTemplate(requestFactory);
			MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
			jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			restTemplate.getMessageConverters().add(jsonHttpMessageConverter);
			restTemplate.getMessageConverters().add(new ResourceHttpMessageConverter(true));
			restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
			restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
			restTemplate.getMessageConverters().add(new AllEncompassingFormHttpMessageConverter());

			PathResource resource = new PathResource(f);

			ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST,
					new HttpEntity<>(resource, headers), new ParameterizedTypeReference<Void>() {
					});

			if (HttpStatus.CREATED.equals(response.getStatusCode()))
				logger.info("indexing is complete");
			else
				logger.warn("indexing was not successful: status code {}", response.getStatusCode());

		} catch (Exception e) {
			logger.error("ooopps", e);
		}
	}

	public void handle2(Path f, String url) {

		try {
			SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
			factory.setBufferRequestBody(false);
			RestTemplate restTemplate = new RestTemplate(factory);

			MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
			jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			restTemplate.getMessageConverters().add(jsonHttpMessageConverter);
			restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", "application/json");
			headers.setContentType(MediaType.APPLICATION_JSON);

			MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
			ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(f)) {
				@Override
				public String getFilename() {
					return f.getFileName().toString();
				}
			};
			data.add("file", resource);

			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
					data, headers);
			ResponseEntity<Map> response = null;

			response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

			if (HttpStatus.CREATED.equals(response.getStatusCode()))
				logger.info("indexing is complete");
			else
				logger.warn("indexing was not successful: status code {}", response.getStatusCode());

		} catch (Exception e) {
			logger.error("ooopps", e);
		}
	}
}
