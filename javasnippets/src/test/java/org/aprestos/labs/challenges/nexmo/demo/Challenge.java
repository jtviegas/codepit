package org.aprestos.labs.challenges.nexmo.demo;

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
		Assert.assertEquals(5, o.solution(new int[] { 1, 3, 6, 4, 1, 2 }));
	}

	@Test
	public void test2() throws Exception {
		Assert.assertEquals(4, o.solution(new int[] { 1, 2, 3 }));
	}

	@Test
	public void test3() throws Exception {
		Assert.assertEquals(1, o.solution(new int[] { -1, -3 }));
	}

}

class Solution {

	public int solution(int[] a) {
		int missing = 0;

		Arrays.sort(a);

		int[] a2 = new int[a.length];
		int removed = 0;
		for (int i = 0, j = 0; i < a.length; i++) {

			if (i == 0 || (i > 0 && a[i] != a[i - 1])) {
				a2[j++] = a[i];
			} else
				removed++;
		}

		if (0 < removed) {
			int[] b = new int[a.length - removed];
			System.arraycopy(a2, 0, b, 0, a.length - removed);
			a = b;
		}

		for (int i = 0; i < a.length; i++) {
			if (0 < a[i] && (i + 1) != a[i]) {
				missing = i + 1;
				break;
			}
		}

		if (0 == missing) {
			if (0 >= a[a.length - 1])
				return 1;
			else
				return a[a.length - 1] + 1;
		}

		return missing;
	}

}
