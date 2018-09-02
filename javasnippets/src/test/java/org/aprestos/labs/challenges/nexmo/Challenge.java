package org.aprestos.labs.challenges.nexmo;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Challenge {

	Solution o = new Solution();

	@Test
	public void test() throws Exception {
		Assert.assertEquals(0, o.solution(new int[] {}));
	}

}

class Solution {

	public int solution(int[] a) {
		return 0;
	}

}
