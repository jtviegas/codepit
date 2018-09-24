package org.aprestos.labs.challenges.expedia;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Three {

	@Test
	public void test() throws Exception {
		Assert.assertEquals(4, solution("20;0,1,10,3,2,4,5,7,6,8,11,9,15,12,13,4,16,18,17,14,11"));
		Assert.assertEquals(0, solution("5;0,1,2,3,0"));

	}

	static int solution(String s) {
		int r = -1;

		String[] params = s.split(";");
		int n = Integer.parseInt(params[0]);
		int[] map = new int[n - 1];
		String[] numbers = params[1].split(",");

		for (String e : numbers) {
			int i = Integer.parseInt(e);
			map[i]++;
			if (1 < map[i]) {
				r = i;
				break;
			}
		}

		return r;
	}

}
