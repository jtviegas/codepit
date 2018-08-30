package org.challenges.norcom.indexer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

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

	@Value("${org.challenges.norcom.indexer.url:#{null}}")
	private String url;
	@Autowired
	private ExecutorService executorService;

	@Test(expected = IllegalArgumentException.class)
	public void noZipFile() {

	}

	@Test
	public void assorted() throws IOException {
		/*
		 * FileProcessor o = new FileProcessor();
		 * 
		 * Map<String,Object> o2 =
		 * o.apply(Paths.get(IndexerTests.class.getClassLoader().getResource("msg1.txt")
		 * .getPath())); System.out.println(new ObjectMapper().writeValueAsString(o2));
		 */

		/*
		 * Assert.assertEquals(3l,
		 * Files.walk(Paths.get(IndexerTests.class.getClassLoader().getResource("one").
		 * getPath())) .filter(o -> !o.toFile().isDirectory()).count());
		 */
		Indexer i = new Indexer(executorService, "{ \"index\" : { \"_index\" : \"consumer\", \"_type\" : \"_doc\" } }");
		i.handle(Paths.get(IndexerTests.class.getClassLoader().getResource("enron.zip").getPath()), url);
	}

}
