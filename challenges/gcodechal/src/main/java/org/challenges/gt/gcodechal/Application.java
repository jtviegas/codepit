package org.challenges.gt.gcodechal;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.challenges.gt.gcodechal.services.Analysis;
import org.challenges.gt.gcodechal.services.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@SpringBootApplication
public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public <T> CommandLineRunner run(final FileHandler<T> fileHandler, final Analysis<T> analysis) {
		LOGGER.trace("[run|in]");
		LOGGER.trace("[run|out]");
		return (args) -> {
			try {
				LOGGER.trace("[CommandLineRunner|in]");
				if (null == args || args.length != 1) {
					throw new IllegalArgumentException("!!! must provide a file path as an argument !!!");
				}
				final Path file = Paths.get(args[0]);
				if (!Files.exists(file, LinkOption.NOFOLLOW_LINKS) || !file.toFile().canRead()) {
					throw new IllegalArgumentException(
							String.format("Cannot find readable file: %s", file.toAbsolutePath().toString()));
				}

				fileHandler.parse(file, analysis);
				analysis.printOutcome(System.out);

			} catch (Exception e) {
				LOGGER.error("[run]", e);
			} finally {
				LOGGER.trace("[CommandLineRunner|out]");
			}

		};
	}
}