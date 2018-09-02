package org.aprestos.labs.challenges.nexmo.one;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Challenge {

	Solution o = new Solution();

	@Test
	public void test() throws Exception {
		Assert.assertEquals(553, o.solution(553));
		Assert.assertEquals(321, o.solution(213));
	}

}

class Solution {

	public int solution(int N) {

		String s = Integer.toString(N);
		int[] a = new int[s.length()];
		int i = 0;
		for (char c : s.toCharArray())
			a[i++] = Character.getNumericValue(c);

		Arrays.sort(a);

		StringBuffer sb = new StringBuffer();
		for (int j = a.length - 1; j >= 0; j--)
			sb.append(String.valueOf(a[j]));

		double r = Double.parseDouble(sb.toString());

		if (100000000 < r)
			return -1;
		else
			return (int) r;
	}

}
