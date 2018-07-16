package org.challenges.csloganalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.challenges.csloganalyzer.model.message.LogEntry;
import org.challenges.csloganalyzer.model.transformation.Transformer;
import org.challenges.csloganalyzer.service.Store;
import org.challenges.csloganalyzer.task.LogSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CsLogAnalizer {

	private static final Logger logger = LoggerFactory.getLogger(CsLogAnalizer.class);

	private final Store store;
	private final ObjectMapper objMapper;
	private final ThreadPoolTaskExecutor executor;
	private final Map<String, LogEntry> logEntries = new HashMap<String, LogEntry>();
	private final Transformer transformer;
	private final List<Future<?>> futures = new ArrayList<Future<?>>();

	public CsLogAnalizer(Store store, ObjectMapper objMapper, ThreadPoolTaskExecutor executor,
			Transformer transformer) {
		logger.debug("[CsLogAnalizer|in] {}", store);
		this.store = store;
		this.objMapper = objMapper;
		this.executor = executor;
		this.transformer = transformer;
		logger.debug("[CsLogAnalizer|out]");
	}

	public void handle(Path file) {
		logger.debug("[handle|in] {}", file);
		BufferedReader reader = null;
		try {

			reader = Files.newBufferedReader(file);
			String line;
			int index = 0;
			while ((line = reader.readLine()) != null && 0 < line.length()) {
				logger.debug("[handle] handling line: {}", ++index);
				handleLogEntry(objMapper.readValue(line, LogEntry.class));
			}

			// wait for tasks to terminate, each one is responsible for its error handling
			for (Future<?> future : futures)
				future.get();

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
		logger.debug("[handle|out]");
	}

	private void handleLogEntry(LogEntry entry) {
		LogEntry mappedEntry = null;
		if (null != (mappedEntry = logEntries.putIfAbsent(entry.getId(), entry))) {
			logger.info("going to transform the line into log and save it [id: {}]", entry.getId());
			futures.add(this.executor.submit(new LogSaver(transformer.apply(mappedEntry, entry), store)));
			logEntries.remove(entry.getId());
		}

	}

}
