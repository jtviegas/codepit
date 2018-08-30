package org.challenges.norcom.indexer;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.challenges.norcom.indexer.services.unzipper.UnzipperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@SpringBootApplication
/* @ComponentScan(basePackageClasses = { RestClient.class }) */
public class Boot {

	private static final Logger logger = LoggerFactory.getLogger(Boot.class);

	public static void main(String[] args) {
		SpringApplication.run(Boot.class, args);
	}

	@Bean
	public CommandLineRunner run(@Value("${org.challenges.norcom.indexer.url:#{null}}") final String url) {
		logger.trace("[run|in] url: {}", url);
		
		return (args) -> {
			Options options = new Options();
			try {
				options.addOption("input", true, "file input to index");
				CommandLineParser parser = new DefaultParser();
				CommandLine line = parser.parse(options, args);
				if (line.hasOption("input")) {
					String filePath = line.getOptionValue("input");
					Path file = Paths.get(filePath);
					if (!Files.exists(file, LinkOption.NOFOLLOW_LINKS))
						throw new IllegalArgumentException(
								String.format("Cannot find file: %s", file.toAbsolutePath().toString()));
					logger.info("going to index file: {}", filePath);
					// new Indexer().handle(file, url);
				} else {
					new HelpFormatter().printHelp("indexer", options);
				}
			} catch (ParseException pe) {
				logger.error("[run]", pe);
				new HelpFormatter().printHelp("indexer", options);
			} catch (Exception e) {
				logger.error("[run]", e);
			} finally {
				logger.trace("[CommandLineRunner|out]");
			}

		};
		logger.trace("[run|out]");
	}
	
	
	private void doit(Path file) {
	    
	  new Indexer().index( new  BulkFilesCreator ( new UnzipperImpl(file).unzip() ).create() );
	  
	  
	    
	}

}
