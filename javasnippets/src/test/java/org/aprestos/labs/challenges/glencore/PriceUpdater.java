package org.aprestos.labs.challenges.glencore;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PriceUpdater {

	@Test
	public void example() {
		String[] items = { "Zinc", "Copper" };
		BigDecimal[] prices = { new BigDecimal("5"), new BigDecimal("2.3") };

		HashMap<String, BigDecimal> expected = new HashMap<>();
		expected.put("Zinc", new BigDecimal("4.5"));
		expected.put("Coal", new BigDecimal("1.1"));
		Assert.assertEquals(expected, PriceUpdater.updatePrices(items, prices, "{Coal:1.1, Zinc:4.5, Copper:0}"));
	}

	public static Map<String, BigDecimal> updatePrices(String[] items, BigDecimal[] prices, String updates) {

		if (items.length != prices.length)
			throw new RuntimeException("items length is different than prices length");

		Map<String, BigDecimal> result = new HashMap<String, BigDecimal>();

		for (int i = 0; i < items.length; i++)
			result.put(items[i], prices[i]);

		String updatesFormat = updates.trim();

		if (updatesFormat.length() > 2 && updatesFormat.charAt(0) == '{'
				&& updatesFormat.charAt(updatesFormat.length() - 1) == '}') {
			updatesFormat = updatesFormat.substring(1, updatesFormat.length() - 1);
			String[] entries = updatesFormat.split(",");
			for (String update : entries) {
				String[] updatePair = update.trim().split(":");
				String element = updatePair[0].trim();
				BigDecimal price = new BigDecimal(updatePair[1].trim());
				if (price.equals(BigDecimal.ZERO)) {
					result.remove(element);
				} else {
					result.put(element, price);
				}
			}
		}

		return result;
	}

}
