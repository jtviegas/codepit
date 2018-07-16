package org.challenges.csloganalyzer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@EnableJpaRepositories("org.challenges.csloganalyzer.model.entity")
@EntityScan("org.challenges.csloganalyzer.model.entity")
@EnableTransactionManagement
@ComponentScan
@EnableAutoConfiguration
@org.springframework.context.annotation.Configuration
public class Configuration {

	@Value("${app.blocking-coefficient}")
	private double blockingCoefficient;

	@Bean
	public ObjectMapper jsonMapper() {
		ObjectMapper mapper = new ObjectMapper().registerModule(new ParameterNamesModule())
				.registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

		return mapper;
	}

	@Bean
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {

		if (1 < blockingCoefficient || 0 > blockingCoefficient)
			throw new RuntimeException("blockingCoefficient should be in between [0.0,1.0]");

		// one decimal only
		blockingCoefficient = ((int) (blockingCoefficient / 0.1)) * 0.1;

		int corePoolSize = Runtime.getRuntime().availableProcessors();
		int maxPoolSize = (int) (blockingCoefficient == 1.0 ? corePoolSize * 10
				: corePoolSize / (1 - blockingCoefficient));

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setThreadNamePrefix("csloganalyzer-pool-");
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.initialize();
		executor.setDaemon(true);
		return executor;
	}
}
