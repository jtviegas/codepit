package org.challenges.norcom.indexer.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FolderProcessor implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(FolderProcessor.class);

	public FolderProcessor() {
		/*
		 * this.log = log; this.store = store;
		 */
	}

	@Override
	public void run() {
		logger.debug("[run|in]");

		try {

			/* logger.info("saved log with id: {}", log.getId()); */
		} catch (Exception e) {
			logger.error(String.format("oopps, difficulties when trying to persist the log with id: %s ...", ""), e);
		}

		logger.debug("[run|out]");
	}

}
