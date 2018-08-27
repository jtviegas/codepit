package org.aprestos.labs.challenges;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Example {

	@Test
	public void test() throws Exception {

		Assert.assertEquals(true, doit());
	}

	private boolean doit() {
		return true;
	}

}
