package org.challenges.rab.statproc;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.RandomStringUtils;
import org.challenges.rab.statproc.StatementProcessorFactory.FileType;
import org.challenges.rab.statproc.exceptions.StatementFormatException;
import org.challenges.rab.statproc.exceptions.StatementProcessorException;
import org.junit.Assert;
import org.junit.Test;

public class StatementProcessorTests {

	@Test(expected = IllegalArgumentException.class)
	public void test_null_csv() throws Exception {
		StatementProcessor processor = StatementProcessorFactory.getStatementProcessor(FileType.CSV);
		processor.process(null);
		Assert.fail();
	}

	@Test(expected = StatementProcessorException.class)
	public void test_nofile_csv() throws Exception {
		StatementProcessor processor = StatementProcessorFactory.getStatementProcessor(FileType.CSV);
		processor.process(Paths.get(RandomStringUtils.random(8, true, false)));
		Assert.fail();
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_null_xml() throws Exception {
		StatementProcessor processor = StatementProcessorFactory.getStatementProcessor(FileType.XML);
		processor.process(null);
		Assert.fail();
	}

	@Test(expected = StatementProcessorException.class)
	public void test_nofile_xml() throws Exception {
		StatementProcessor processor = StatementProcessorFactory.getStatementProcessor(FileType.XML);
		processor.process(Paths.get(RandomStringUtils.random(8, true, false)));
		Assert.fail();
	}

	@Test
	public void test_csv_valid_statements() throws Exception {

		StatementProcessor processor = StatementProcessorFactory.getStatementProcessor(FileType.CSV);
		Path records = Paths
				.get(StatementProcessorTests.class.getClassLoader().getResource("records_valid.csv").toURI());
		String[] expected = new String[] {};
		String[] actual = processor.process(records);
		Assert.assertArrayEquals(expected, actual);

	}

	@Test
	public void test_csv_notvalid_statement() throws Exception {

		StatementProcessor processor = StatementProcessorFactory.getStatementProcessor(FileType.CSV);
		Path records = Paths
				.get(StatementProcessorTests.class.getClassLoader().getResource("records_notvalid.csv").toURI());

		String[] expected = new String[] { "156360,Candy for Jan King" };
		String[] actual = processor.process(records);
		Assert.assertArrayEquals(expected, actual);

	}

	@Test
	public void test_csv_notvalid_duplicated() throws Exception {

		StatementProcessor processor = StatementProcessorFactory.getStatementProcessor(FileType.CSV);

		Path records = Paths.get(StatementProcessorTests.class.getClassLoader()
				.getResource("records_notvalid_duplicated.csv").toURI());

		String[] expected = new String[] { "112806,Clothes from Peter de Vries", "112806,Clothes for Daniel Theue" };
		String[] actual = processor.process(records);
		Assert.assertArrayEquals(expected, actual);

	}

	@Test(expected = StatementFormatException.class)
	public void test_csv_bad() throws Exception {

		StatementProcessor processor = StatementProcessorFactory.getStatementProcessor(FileType.CSV);
		Path records = Paths.get(StatementProcessorTests.class.getClassLoader().getResource("bad.csv").toURI());
		processor.process(records);
		Assert.fail();
	}

	@Test
	public void test_csv_empty() throws Exception {
		StatementProcessor processor = StatementProcessorFactory.getStatementProcessor(FileType.CSV);
		Path records = Paths.get(StatementProcessorTests.class.getClassLoader().getResource("empty.csv").toURI());
		String[] expected = new String[] {};
		String[] actual = processor.process(records);
		Assert.assertArrayEquals(expected, actual);

	}

	@Test
	public void test_csv_empty_really() throws Exception {
		StatementProcessor processor = StatementProcessorFactory.getStatementProcessor(FileType.CSV);
		Path records = Paths
				.get(StatementProcessorTests.class.getClassLoader().getResource("really_empty.csv").toURI());
		String[] expected = new String[] {};
		String[] actual = processor.process(records);
		Assert.assertArrayEquals(expected, actual);

	}

	@Test
	public void test_xml_valid_statements() throws Exception {

		StatementProcessor processor = StatementProcessorFactory.getStatementProcessor(FileType.XML);
		Path records = Paths
				.get(StatementProcessorTests.class.getClassLoader().getResource("records_valid.xml").toURI());
		String[] expected = new String[] {};
		String[] actual = processor.process(records);
		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	public void test_xml_notvalid_statements() throws Exception {

		StatementProcessor processor = StatementProcessorFactory.getStatementProcessor(FileType.XML);
		Path records = Paths
				.get(StatementProcessorTests.class.getClassLoader().getResource("records_notvalid.xml").toURI());
		String[] expected = new String[] { "156360,Candy for Jan King", "115256,Candy from Rik Theuß" };
		String[] actual = processor.process(records);
		Assert.assertArrayEquals(expected, actual);
	}

	@Test(expected = StatementFormatException.class)
	public void test_xml_bad() throws Exception {

		StatementProcessor processor = StatementProcessorFactory.getStatementProcessor(FileType.XML);
		Path records = Paths.get(StatementProcessorTests.class.getClassLoader().getResource("bad.xml").toURI());
		processor.process(records);
		Assert.fail();
		;

	}

	@Test
	public void test_xml_empty() throws Exception {
		StatementProcessor processor = StatementProcessorFactory.getStatementProcessor(FileType.XML);
		Path records = Paths.get(StatementProcessorTests.class.getClassLoader().getResource("empty.xml").toURI());
		String[] expected = new String[] {};
		String[] actual = processor.process(records);
		Assert.assertArrayEquals(expected, actual);

	}

	@Test
	public void test_xml_empty_really() throws Exception {
		StatementProcessor processor = StatementProcessorFactory.getStatementProcessor(FileType.XML);
		Path records = Paths
				.get(StatementProcessorTests.class.getClassLoader().getResource("really_empty.xml").toURI());
		String[] expected = new String[] {};
		String[] actual = processor.process(records);
		Assert.assertArrayEquals(expected, actual);

	}

}
