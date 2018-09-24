package org.aprestos.labs.challenges.klarna.phatwo;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Challenge {

	@Test
	public void test() throws Exception {
	  Assert.assertEquals(2, Solution.maxProfit(new int[] {1,2,3}));
		Assert.assertEquals(12, Solution.maxProfit(new int[] {1,2,3,2,4,6,4,13,5,6}));
	}

}

class Solution {

	public static int maxProfit(int[] prices) {

		List<Integer> yields = new LinkedList<Integer>();
		int buy = Integer.MAX_VALUE;
		int yield = 0;
		
		for (int i = 0; i < prices.length; i++) {
		  if( prices[i] < buy ) {
		    if(yield > 0)
		      yields.add(yield);
		    buy = prices[i];
		    yield = 0;
		  } 
		  else if( (prices[i] - buy) > yield ) {
		    yield = prices[i] - buy;
		  }
		}
		
		if(yield > 0)
          yields.add(yield);
		if( 0 < yields.size())
		  return Collections.max(yields);
		else
		  return 0;
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
