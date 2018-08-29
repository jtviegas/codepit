package org.challenges.norcom.indexer.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileProcessor {

	private static final Logger logger = LoggerFactory.getLogger(FileProcessor.class);

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

	public Map<String, Object> handle(Path file) {
		logger.trace("[handle|in] {}", file.toString());
		Map<String, Object> r = new HashMap<String, Object>();

		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(file);
			String line = null;
			int index = 0;
			while ((line = reader.readLine()) != null && 0 < line.length()) {
				logger.debug("[handle] handling line: {}", ++index);
				Map.Entry<String, String> entry = handleHeaderLine(line);
				if (null != entry)
					r.put(entry.getKey(), entry.getValue());
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
		logger.trace("[handle|out]");
		return r;
	}

}
