package org.challenges.csloganalyzer.task;

import org.challenges.csloganalyzer.model.entity.Log;
import org.challenges.csloganalyzer.service.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogSaver implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(LogSaver.class);

	private final Log log;
	private final Store store;

	public LogSaver(Log log, Store store) {
		this.log = log;
		this.store = store;
	}

	@Override
	public void run() {
		logger.debug("[run|in]");

		try {
			store.saveLog(log);
			logger.info("saved log with id: {}", log.getId());
		} catch (Exception e) {
			logger.error(
					String.format("oopps, difficulties when trying to persist the log with id: %s ...", log.getId()),
					e);
		}

		logger.debug("[run|out]");
	}

}
