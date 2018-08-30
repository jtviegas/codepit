package org.challenges.norcom.indexer.services.bulkfiles.mailbox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

class FolderProcessor implements Function<Path, List<String>> {

	private static final Logger logger = LoggerFactory.getLogger(FolderProcessor.class);

	private String metadata;

	public FolderProcessor(String metadata) throws JsonProcessingException {
		logger.trace("[()] IN metadata:{}", metadata);
		this.metadata = metadata;
		logger.trace("[()] OUT");
	}

	@Override
	public List<String> apply(Path t) {
		logger.trace("[apply] IN t:{}", t);
		try {
			// each file has one line that ends up combined with metadata
			List<String> r = Files.walk(t).filter(f -> !f.toFile().isDirectory()).map(new EmailFileProcessor())
					.map(s -> Stream.of(metadata, s)).flatMap(x -> x).collect(Collectors.toList());
			logger.info("successfully processed path: {}", t.toString());
			return r;
		} catch (IOException e) {
			throw new RuntimeException(String.format("couldn't process folder %s", t.toString()));
		} finally {
			logger.trace("[apply] OUT");
		}
	}

}
