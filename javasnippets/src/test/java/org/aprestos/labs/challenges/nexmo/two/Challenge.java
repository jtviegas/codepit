package org.aprestos.labs.challenges.nexmo.two;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Challenge {

	Solution o = new Solution();

	@Test
	public void test() throws Exception {
		Assert.assertEquals(false, o.solution(new Object[] { "3", "1" }, new Object[] { "S", "D", "3" }));
	}

}

class Solution {

	public boolean solution(Object[] a, Object[] b) {

		boolean result = true;

		Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
		for (int i = 0; i < b.length; i++)
			map.put(b[i].hashCode(), true);

		for (int i = 0; i < a.length; i++)
			if (null == map.get(a[i].hashCode()))
				return false;

		return result;
	}

}