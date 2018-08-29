package org.challenges.norcom.indexer.tasks;

import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FolderHandler implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(FolderHandler.class);
	private Path source, destination;
	
	public FolderHandler(Path sourceFolder, Path destinationFolder) {
	  
	  if (! (Files.exists(sourceFolder) && Files.isDirectory(sourceFolder) && Files.isReadable(sourceFolder)) ) {
      throw new IllegalArgumentException("source is not a readable directory");
    }
	  
	  if (! (Files.exists(destinationFolder) && Files.isDirectory(destinationFolder) && Files.isWritable(destinationFolder)) ) {
      throw new IllegalArgumentException("destination is not a writable directory");
    }
	  
	  this.source = sourceFolder;
	  this.destination = destinationFolder;
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
