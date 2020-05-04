package org.challenges.gt.gcodechal.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.challenges.gt.gcodechal.model.AddressBookEntry;
import org.challenges.gt.gcodechal.model.Line2AddressBookEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FileHandlerImpl implements FileHandler<AddressBookEntry> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileHandlerImpl.class);
	private final Line2AddressBookEntry transformer = new Line2AddressBookEntry();

	@Override
	public void parse(final Path file, final Analysis<AddressBookEntry> analyser) throws IOException {

		LOGGER.trace("[parse|in] {}", file.toString());

		BufferedReader reader = null;
		try {

			reader = Files.newBufferedReader(file);
			String line = null;
			int index = 0;
			while ((line = reader.readLine()) != null && 0 < line.length()) {
				LOGGER.debug("[handle] handling line {}: {}", ++index, line);
				analyser.analyse(transformer.apply(line.trim()));
			}

		} finally {
			if (null != reader)
				try {
					reader.close();
				} catch (IOException e2) {
					LOGGER.error("[handle]...when trying to close the reader...", e2);
				}
			LOGGER.trace("[handle|out]");
		}

	}

}
