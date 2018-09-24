package org.aprestos.labs.challenges.nexmo.three;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {

	private ByteArrayOutputStream stdOut;
	private OS os;
	private Scheduler scheduler;

	@Before
	public void init() {
		stdOut = new ByteArrayOutputStream();
		os = new OS(stdOut);
		scheduler = new Scheduler(os);
	}

	@After
	public void shutdown() throws IOException {
		stdOut = new ByteArrayOutputStream();
		scheduler.shutdown();
		os.shutdown();
		stdOut.close();
	}

	@Test
	public void test_01() throws Exception {
		scheduler.Set_timer2(1000, CB.create("S"));
		scheduler.Set_timer2(300, CB.create("A"));
		Thread.sleep(8000);
		Assert.assertEquals("AS", stdOut.toString());
	}

	@Test
	public void test_02() throws Exception {
		scheduler.Set_timer2(1000, CB.create("S"));
		Thread.sleep(1001);
		scheduler.Set_timer2(300, CB.create("A"));
		Thread.sleep(8000);
		Assert.assertEquals("SA", stdOut.toString());
	}

	@Test
	public void test_03() throws Exception {
		scheduler.Set_timer2(1000, CB.create("A"));
		scheduler.Set_timer2(1010, CB.create("B"));
		scheduler.Set_timer2(1020, CB.create("C"));
		scheduler.Set_timer2(1001, CB.create("b"));
		scheduler.Set_timer2(100, CB.create("a"));
		Thread.sleep(12000);
		Assert.assertEquals("aAbBC", stdOut.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_04() throws Exception {
		scheduler.Set_timer2(1000, null);
	}

	@Test
	public void test_05() throws Exception {
		scheduler.Set_timer2(1, CB.create("A"));
		scheduler.Set_timer2(3, CB.create("B"));
		scheduler.Set_timer2(4, CB.create("C"));
		scheduler.Set_timer2(2, CB.create("b"));
		scheduler.Set_timer2(0, CB.create("a"));
		Thread.sleep(8000);
		Assert.assertEquals("aAbBC", stdOut.toString());
	}

}
