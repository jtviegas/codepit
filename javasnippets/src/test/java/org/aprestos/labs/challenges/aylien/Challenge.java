package org.aprestos.labs.challenges.aylien;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Challenge {

	@Test
	public void test() throws Exception {
		Assert.assertEquals(2, countDuplicates(new int[] { 1, 3, 1, 4, 5, 6, 3, 2 }));
	}

	static int countDuplicates(int[] numbers) {
		int result = 0;
		int[] map = new int[1000];

		for (int i = 0; i < numbers.length; i++)
			map[numbers[i] - 1]++;

		for (int i = 0; i < map.length; i++)
			if (map[i] > 1)
				result++;

		return result;
	}

}
