package org.aprestos.labs.assorted;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Fibonnacci {

	// 0, 1, 2, 3, 4, 5, 6, 7
	// 0, 1, 1, 2, 3, 5, 8, 13
	private int doit(int n) {
		int r = 0;

		if (0 == n)
			return 0;
		if (1 == n || 2 == n)
			return 1;

		int pp = 1, p = 1;
		for (int i = 3; i <= n; i++) {
			r = pp + p;
			pp = p;
			p = r;
		}

		return r;
	}

	@Test
	public void test() throws Exception {

		Assert.assertEquals(8, doit(6));
		Assert.assertEquals(13, doit(7));

	}

}
