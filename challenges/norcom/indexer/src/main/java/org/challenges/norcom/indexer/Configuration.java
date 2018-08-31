package org.challenges.norcom.indexer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

@ComponentScan
@EnableAutoConfiguration
@org.springframework.context.annotation.Configuration
public class Configuration {

	@Value("${org.challenges.norcom.indexer.blocking-coefficient}")
	private double blockingCoefficient;

	@Bean
	public ExecutorService executorService() {

		if (1 < blockingCoefficient || 0 > blockingCoefficient)
			throw new RuntimeException("blockingCoefficient should be in between [0.0,1.0]");

		// one decimal only
		blockingCoefficient = ((int) (blockingCoefficient / 0.1)) * 0.1;

		int corePoolSize = Runtime.getRuntime().availableProcessors();
		int maxPoolSize = (int) (blockingCoefficient == 1.0 ? corePoolSize * 10
				: corePoolSize / (1 - blockingCoefficient));

		ExecutorService pool = Executors.newFixedThreadPool(maxPoolSize,
				new ThreadFactoryBuilder().setDaemon(true).setNameFormat("Indexer-pool-%d").build());

		return pool;
	}

	@Bean(name = "metadata")
	public String metadata(@Value("${org.challenges.norcom.indexer.index.type}") String indexType,
			@Value("${org.challenges.norcom.indexer.index.name}") String indexName) throws JsonProcessingException {

		Map<String, Object> index = new HashMap<>();
		index.put("_index", indexName);
		index.put("_type", indexType);
		Map<String, Object> metadata = new HashMap<>();
		metadata.put("index", index);
		return new ObjectMapper().writeValueAsString(metadata);

	}
}
