package org.challenges.norcom.indexer.sources.mailboxes;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmailFileProcessor implements Function<Path, String> {

	private static final Logger logger = LoggerFactory.getLogger(EmailFileProcessor.class);
	private static final ObjectMapper jsonMapper = new ObjectMapper();

	@Override
	public String apply(Path t) {
		try {
			return jsonMapper.writeValueAsString(handle(t));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(String.format("could not parse file %s", t.toString()), e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map.Entry<String, String> handleHeaderLine(String l) {
		logger.trace("[handleHeaderLine|in] {}", l);
		Map.Entry<String, String> r = null;
		int fieldSeparation = l.indexOf(":");
		String key = l.substring(0, fieldSeparation);
		String value = l.substring(fieldSeparation + 1);
		if (null != value && 0 < value.trim().length())
			r = new AbstractMap.SimpleEntry(key, value.trim());
		logger.trace("[handleHeaderLine|out] {}", r);
		return r;
	}

	private Map<String, Object> handle(Path file) {
		logger.trace("[handle|in] {}", file.toString());
		Map<String, Object> r = new HashMap<String, Object>();

		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(file);
			String line = null;
			int index = 0;

			Map.Entry<String, String> entry = null;
			while ((line = reader.readLine()) != null && 0 < line.length()) {
				logger.debug("[handle] handling line: {}", ++index);
				int fieldSeparation = line.indexOf(":");

				if (-1 < fieldSeparation) {
					if (null != entry)
						r.put(entry.getKey(), entry.getValue());
					entry = handleHeaderLine(line);
				} else
					entry.setValue(String.format("%s%s", entry.getValue(), line.trim()));

			}
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				logger.debug("[handle] handling line: {}", ++index);
				if (0 < line.length()) {
					sb.append(line);
					sb.append(System.getProperty("line.separator"));
				}
			}
			r.put("body", sb.toString().trim());
		} catch (Exception e1) {
			logger.error("[handle]...ooppss...", e1);
		} finally {
			if (null != reader)
				try {
					reader.close();
				} catch (IOException e2) {
					logger.error("[handle]...when trying to close the reader...", e2);
				}
		}
		logger.trace("[handle|out] [{}]", r);
		return r;
	}

}
