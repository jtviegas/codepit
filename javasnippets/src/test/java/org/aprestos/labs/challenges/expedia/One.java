package org.aprestos.labs.challenges.expedia;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class One {

	@Test
	public void test() throws Exception {
		Assert.assertEquals(5, fibonacci(5));
		Assert.assertEquals(144, fibonacci(12));
		Assert.assertEquals(1, fibonacci(1));
		Assert.assertEquals(0, fibonacci(0));
	}

	static int fibonacci(int n) {

		int r = 0;

		if (0 == n)
			return 0;
		if (1 == n)
			return 1;

		int pp = 0, p = 1;
		for (int i = 2; i <= n; i++) {
			r = pp + p;
			pp = p;
			p = r;
		}

		return r;
	}

}
