package org.aprestos.labs.snippets.impl.assorted;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class RandomTest {

	Random r = new Random();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		int iteration=0;
		while(20 > iteration++)
			System.out.println(r.nextInt(6));
	}

}
