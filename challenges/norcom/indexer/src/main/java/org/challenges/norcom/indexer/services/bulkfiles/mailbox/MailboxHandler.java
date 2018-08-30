package org.challenges.norcom.indexer.services.bulkfiles.mailbox;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MailboxHandler implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(MailboxHandler.class);
	private final Path source;
	private final Path destination;
	private final String metadata;

	public MailboxHandler(Path sourceFolder, Path destinationFolder, String metadata) {

		if (!(Files.exists(sourceFolder) && Files.isDirectory(sourceFolder) && Files.isReadable(sourceFolder)))
			throw new IllegalArgumentException("source is not a readable directory");

		if (!(Files.exists(destinationFolder) && Files.isDirectory(destinationFolder)
				&& Files.isWritable(destinationFolder)))
			throw new IllegalArgumentException("destination is not a writable directory");

		this.source = sourceFolder;
		this.destination = destinationFolder;
		this.metadata = metadata;
	}

	@Override
	public void run() {
		logger.debug("[run|in]");

		try {

			List<String> lines = new FolderProcessor(metadata).apply(source);
			Path output = Files
					.createFile(Paths.get(destination.toAbsolutePath().toString(), UUID.randomUUID().toString()));
			writeOutput(lines, output);

		} catch (Exception e) {
			logger.error(String.format("oopps, difficulties when trying to persist the log with id: %s ...", ""), e);
		}

		logger.debug("[run|out]");
	}

	private void writeOutput(List<String> lines, Path output) {
		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output.toFile()), "utf-8"));
			for (String line : lines) {
				writer.write(line);
				writer.write(System.getProperty("line.separator"));
			}
			logger.info("successfully saved output: {}", output.toString());
		} catch (IOException e) {
			logger.error("couldn't save the output", e);
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}
	}

}
