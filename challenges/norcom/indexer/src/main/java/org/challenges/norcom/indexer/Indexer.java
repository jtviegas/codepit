package org.challenges.norcom.indexer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import org.challenges.norcom.indexer.components.Unzipper;
import org.challenges.norcom.indexer.sources.mailboxes.MailboxHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.SerializationFeature;

public class Indexer {

	private static final Logger logger = LoggerFactory.getLogger(Indexer.class);
	private final ExecutorService executor;
	private final String metadata;

	public Indexer(ExecutorService executor, String metadata) {
		this.executor = executor;
		this.metadata = metadata;
		/* this.client = restClient; */

		/*
		 * RestHighLevelClient client = new RestHighLevelClient( RestClient.builder( new
		 * HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9201,
		 * "http")));
		 */

	}

	public void handle(Path f, String url) {

		try {
			String srcFolder = unzip(f);
			Path outputFolder = createBulkFiles(srcFolder);

		} catch (IllegalArgumentException ia) {
			throw ia;
		} catch (Exception e) {
			logger.error("ooopps", e);
		}
	}

	Path createBulkFiles(final String folder) {

		Path outputFolder = Paths.get(String.format("%s%s%s", System.getProperty("java.io.tmpdir"),
				System.getProperty("file.separator"), UUID.randomUUID().toString()));
		outputFolder.toFile().mkdirs();

		List<CompletableFuture<Void>> tasks = Arrays.asList(Paths.get(folder).toFile().listFiles()).stream()
				.filter(o -> o.isDirectory())
				.map(d -> CompletableFuture
						.runAsync(new MailboxHandler(Paths.get(d.getAbsolutePath()), outputFolder, metadata), executor))
				.collect(Collectors.toList());
		CompletableFuture<Void> futures = CompletableFuture.allOf(tasks.toArray(new CompletableFuture<?>[] {}));

		futures.join();
		return outputFolder;
	}

	public void listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				System.out.println(fileEntry.getName());
			}
		}
	}

	String unzip(Path file) throws IOException {

		String destinationFolder = String.format("%s%s%s", System.getProperty("java.io.tmpdir"),
				System.getProperty("file.separator"), UUID.randomUUID().toString());
		File folder = new File(destinationFolder);
		folder.mkdirs();

		new Unzipper(file, Paths.get(destinationFolder)).unzip();
		return destinationFolder;
	}

	private void bulkPost(Path f, String url) {
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

		ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(resource, headers),
				new ParameterizedTypeReference<Void>() {
				});

		if (HttpStatus.OK.equals(response.getStatusCode()))
			logger.info("indexing is complete");
		else
			logger.warn("indexing was not successful: status code {}", response.getStatusCode());
	}

}
