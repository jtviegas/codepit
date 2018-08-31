package org.challenges.norcom.indexer.services.bulkfiles.mailbox;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import org.challenges.norcom.indexer.services.bulkfiles.BulkFilesCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BulkFilesCreatorFromMailboxes implements BulkFilesCreator {

	private static final Logger logger = LoggerFactory.getLogger(BulkFilesCreatorFromMailboxes.class);

	@Autowired
	private ExecutorService executorService;
	@Autowired
	private String metadata;

	@Override
	public Path create(Path sourceFolder) {

		Path outputFolder = createOutputFolder();
		File mainFolder = sourceFolder.toFile();

		while (1 == mainFolder.listFiles().length)
			mainFolder = mainFolder.listFiles()[0];

		List<CompletableFuture<Void>> tasks = Arrays.asList(mainFolder.listFiles()).stream()
				.filter(o -> o.isDirectory())
				.map(d -> CompletableFuture.runAsync(
						new MailboxHandler(Paths.get(d.getAbsolutePath()), outputFolder, metadata), executorService))
				.collect(Collectors.toList());
		CompletableFuture<Void> futures = CompletableFuture.allOf(tasks.toArray(new CompletableFuture<?>[] {}));
		futures.join();

		return outputFolder;

	}

	private Path createOutputFolder() {
		Path outputFolder = Paths.get(String.format("%s%s%s", System.getProperty("java.io.tmpdir"),
				System.getProperty("file.separator"), UUID.randomUUID().toString()));
		outputFolder.toFile().mkdirs();
		return outputFolder;
	}

}
