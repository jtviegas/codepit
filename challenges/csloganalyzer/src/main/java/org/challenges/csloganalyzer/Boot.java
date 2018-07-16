package org.challenges.csloganalyzer;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.challenges.csloganalyzer.model.transformation.Transformer;
import org.challenges.csloganalyzer.service.Store;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.fasterxml.jackson.databind.ObjectMapper;

@Profile("!test")
@SpringBootApplication
public class Boot {

	private static final Logger logger = LoggerFactory.getLogger(Boot.class);

	public static void main(String[] args) {
		SpringApplication.run(Boot.class, args);
	}

	@Bean
	public CommandLineRunner run(Store store, Transformer transformer, ObjectMapper jsonMapper,
			ThreadPoolTaskExecutor threadPoolTaskExecutor) {
		logger.trace("[run|in]");
		logger.trace("[run|out]");
		return (args) -> {
			try {
				logger.trace("[CommandLineRunner|out]");
				if (null == args || args.length != 1)
					throw new RuntimeException("!!! must provide a file path as an argument !!!");
				Path file = Paths.get(args[0]);
				if (!Files.exists(file, LinkOption.NOFOLLOW_LINKS))
					throw new IllegalArgumentException(
							String.format("Cannot find file: %s", file.toAbsolutePath().toString()));

				Assert.assertNotNull(store);
				new CsLogAnalizer(store, jsonMapper, threadPoolTaskExecutor, transformer).handle(file);
			} catch (Exception e) {
				logger.error("[run]", e);
			} finally {
				logger.trace("[CommandLineRunner|out]");
			}

		};
	}

}
