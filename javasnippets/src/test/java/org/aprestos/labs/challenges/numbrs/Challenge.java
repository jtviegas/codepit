package org.aprestos.labs.challenges.numbrs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Challenge {

	Solution o = new Solution();

	@Test
	public void test() throws Exception {
		Assert.assertEquals(50, Solution.budgetShopping(50, Arrays.asList(2, 20, 19), Arrays.asList(2, 24, 20)));
	}

}

class Solution {

	static int budgetShopping(int n, List<Integer> bundleQuantities, List<Integer> bundleCosts) {
		int r = 0;
		double budget = n;
		Map<Double, Integer> ratios = new TreeMap<Double, Integer>(new Comparator<Double>() {

			@Override
			public int compare(Double o1, Double o2) {
				return -1 * o2.compareTo(o1);
			}

		});

		for (int i = 0; i < bundleQuantities.size(); i++) {
			Integer q = bundleQuantities.get(i);
			Integer c = bundleCosts.get(i);
			ratios.put(new Double((c.intValue() * 1.0) / (q.intValue() * 1.0)), i);
		}

		for (Map.Entry<Double, Integer> e : ratios.entrySet()) {
			double price = bundleCosts.get(e.getValue());
			int quant = bundleQuantities.get(e.getValue());

			if (budget > price) {
				int purchases = (int) (budget / price);
				budget -= purchases * price;
				r += purchases * quant;
			}
		}

		return r;
	}

}
