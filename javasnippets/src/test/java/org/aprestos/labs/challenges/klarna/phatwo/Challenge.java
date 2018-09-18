package org.aprestos.labs.challenges.klarna.phatwo;

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

	public static int maxProfit(int[] prices) {
		int r;

		int lp = 0;
		int hp = prices.length - 1;
		int diff = prices[hp] - prices[lp];

		for (int i = 0; i < prices.length - 1; i++) {

		}

		return r;
	}

	public static int maxProfitDumb(int[] prices) {
		int r;

		int buyTs = 0;
		int sellTs = 0;
		int maxyield = 0;

		for (int i = 0; i < prices.length - 1; i++) {
			int buy = prices[i];
			for (int j = 1; j < prices.length; j++) {
				int sell = prices[j];
				int yield = sell - buy;
				if (yield > maxyield) {
					maxyield = yield;
					buyTs = i;
					sellTs = j;
				}

			}

		}

		return maxyield;
	}

}
