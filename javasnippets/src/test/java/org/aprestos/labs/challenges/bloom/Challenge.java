package org.aprestos.labs.challenges.bloom;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Challenge {

	Solution o = new Solution();

	@Test
	public void test() throws Exception {
		Assert.assertEquals(123, o.parseInt("123", 10)); // return integer 123
		Assert.assertEquals(10, o.parseInt("a", 16)); // return integer 10
	}

}

class Solution {

	static int[] nums = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	int fromChar(char c) {
		int r = -1;
		for (int i = 0; i < nums.length; i++)
			if (c == nums[i]) {
				r = i;
				break;
			}
		return r;
	}

	int parseInt(String s, int b) {

		int result = 0;
		char[] numberChar = s.toCharArray();
		int j = 0;
		for (int i = numberChar.length - 1; i >= 0; i--)
			result += Math.pow(b, j++) * fromChar(numberChar[i]);

		return result;
	}

}
