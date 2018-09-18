package org.aprestos.labs.challenges.supawe;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	public void test_one() {
		Assert.assertEquals(new BigInteger("100001"), new App().doIt(new String[] { "100000", "1" }));
	}

	@Test(expected = NumberFormatException.class)
	public void test_nfe() {
		Assert.assertEquals(new BigInteger("1000c01"), new App().doIt(new String[] { "100000", "1" }));
	}

	@Test
	public void test_big() {
		Assert.assertEquals(new BigInteger(
				"109999999999999999999999999999999999999999999999999999999990000000000000000000000009999999999991"),
				new App().doIt(new String[] {
						"109999999999999999999999999999999999999999999999999999999990000000000000000000000009999999999990",
						"1" }));
	}
}
