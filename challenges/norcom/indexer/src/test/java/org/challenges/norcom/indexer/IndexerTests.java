package org.challenges.norcom.indexer;

import java.nio.file.Paths;
import java.util.Map;

import org.challenges.norcom.indexer.tasks.FileProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class IndexerTests {

	@Value("${org.challenges.norcom.indexer.url:#{null}}")
	private String url;

	@Test(expected = IllegalArgumentException.class)
	public void noZipFile() {
		Indexer i = new Indexer();
		i.handle(Paths.get("pom.xml"), url);
	}

	@Test
	public void assorted() throws JsonProcessingException {
		FileProcessor o = new FileProcessor();

		 Map<String,Object> o2 = o.apply(Paths.get(IndexerTests.class.getClassLoader().getResource("msg1.txt").getPath()));
		 System.out.println(new ObjectMapper().writeValueAsString(o2));
	}

}
