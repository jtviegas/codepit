package org.challenges.adthena.shopbasket.model;

import java.math.BigDecimal;

public class PriceUtils {

	private static final String POUND_PATTERN = "£%.2f", PENCE_PATTERN = "%dp";

	public static String toString(BigDecimal price) {
		String result = null;

		BigDecimal roundedPrice = price.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		if (-1 == roundedPrice.abs().compareTo(BigDecimal.ONE))
			result = String.format(PENCE_PATTERN, roundedPrice.multiply(new BigDecimal("100.0")).intValue());
		else
			result = String.format(POUND_PATTERN, roundedPrice.doubleValue());

		return result;
	}

}
