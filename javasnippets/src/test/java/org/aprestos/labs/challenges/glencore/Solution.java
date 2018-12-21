package org.aprestos.labs.challenges.glencore;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Solution {

	@Test
	public void test() throws Exception {
		Assert.assertEquals(23, new Solution().solution(10));
	}

	public int solution(int number) {
		int result = 0;
		List<Integer> multiples = new ArrayList<Integer>();
		for (int i = 0; i < number; i++) {
			if (0 == i % 3 || 0 == i % 5)
				multiples.add(i);
		}

		for (Integer i : multiples)
			result += i.intValue();

		return result;
	}

}
