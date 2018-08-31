package org.challenges.norcom.indexer;

import java.io.IOException;
import java.nio.file.Paths;

import org.challenges.norcom.indexer.services.bulkfiles.BulkFilesCreator;
import org.challenges.norcom.indexer.services.indexer.Indexer;
import org.challenges.norcom.indexer.services.unzipper.Unzipper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@SpringBootTest(classes = { Configuration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class IndexerTests {

	@Autowired
	private Indexer indexer;
	@Autowired
	private BulkFilesCreator bulkFilesCreator;
	@Autowired
	private Unzipper unzipper;

	@Value("${org.challenges.norcom.indexer.url:#{null}}")
	private String url;

	@Test
	public void assorted() throws IOException {

		indexer.index(bulkFilesCreator.create(
				unzipper.unzip(Paths.get(IndexerTests.class.getClassLoader().getResource("enron.zip").getPath()))));

	}

}
