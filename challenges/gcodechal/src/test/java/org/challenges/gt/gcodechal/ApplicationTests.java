package org.challenges.gt.gcodechal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.challenges.gt.gcodechal.model.AddressBookEntry;
import org.challenges.gt.gcodechal.services.Analysis;
import org.challenges.gt.gcodechal.services.FileHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Config.class })
public class ApplicationTests {

	@Autowired
	private FileHandler<AddressBookEntry> fileHandler;
	@Autowired
	private Analysis<AddressBookEntry> analysis;

	private ByteArrayOutputStream stdOut;

	@Before
	public void init() {
		stdOut = new ByteArrayOutputStream();

	}

	@After
	public void shutdown() throws IOException {
		analysis.reset();
		stdOut.close();
	}

	@Test
	public void simple_AddressBook() throws IOException {

		fileHandler.parse(
				Paths.get(Thread.currentThread().getContextClassLoader().getResource("AddressBook").getPath()),
				analysis);
		analysis.printOutcome(stdOut);

		Assert.assertEquals("How many males are in the address book? 3" + System.getProperty("line.separator")
				+ "Who is the oldest person in the address book? Wes Jackson" + System.getProperty("line.separator")
				+ "How many days older is Bill than Paul? 2862" + System.getProperty("line.separator"),
				stdOut.toString());

	}

	@Test
	public void empty_AddressBook() throws IOException {

		fileHandler.parse(
				Paths.get(Thread.currentThread().getContextClassLoader().getResource("AddressBookEmpty").getPath()),
				analysis);
		analysis.printOutcome(stdOut);

		Assert.assertEquals(
				"How many males are in the address book? 0" + System.getProperty("line.separator")
						+ "Who is the oldest person in the address book? null" + System.getProperty("line.separator")
						+ "How many days older is Bill than Paul? 0" + System.getProperty("line.separator"),
				stdOut.toString());

	}

	@Test(expected = IllegalStateException.class)
	public void wrong_AddressBook() throws IOException {

		fileHandler.parse(
				Paths.get(Thread.currentThread().getContextClassLoader().getResource("AddressBookWrongLine").getPath()),
				analysis);
		Assert.fail();

	}

}
