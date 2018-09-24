package org.aprestos.labs.challenges.expedia;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Two {

	private static final Map<String, Double> currencies = new LinkedHashMap<String, Double>();

	static {
		currencies.put("ONE HUNDRED", 100.00);
		currencies.put("FIFTY", 50.00);
		currencies.put("TWENTY", 20.00);
		currencies.put("TEN", 10.00);
		currencies.put("FIVE", 5.00);
		currencies.put("TWO", 2.00);
		currencies.put("ONE", 1.00);
		currencies.put("HALF DOLLAR", 0.50);
		currencies.put("QUARTER", 0.25);
		currencies.put("DIME", 0.10);
		currencies.put("NICKEL", 0.05);
		currencies.put("PENNY", 0.01);
	}

	@Test
	public void test() throws Exception {

		Assert.assertEquals("NICKEL,PENNY", solution(15.94, 16.00));
		Assert.assertEquals("ERROR", solution(17, 16.00));
		Assert.assertEquals("ZERO", solution(35, 35));
		Assert.assertEquals("FIVE", solution(45, 50));

	}

	static String solution(double bill, double pay) {

		List<String> r = new ArrayList<String>();
		if (0 < Double.compare(bill, pay))
			return "ERROR";

		if (0 == Double.compare(bill, pay))
			return "ZERO";

		double change = pay - bill;

		for (Map.Entry<String, Double> e : currencies.entrySet()) {
			while (e.getValue() <= change) {
				change -= e.getValue();
				r.add(e.getKey());
			}
		}

		return String.join(",", r);
	}

}
