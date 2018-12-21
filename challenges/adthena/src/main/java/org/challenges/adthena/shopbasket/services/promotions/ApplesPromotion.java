package org.challenges.adthena.shopbasket.services.promotions;

import java.math.BigDecimal;
import java.util.function.Consumer;

import org.challenges.adthena.shopbasket.model.BasketItem;
import org.challenges.adthena.shopbasket.model.PriceUtils;

public class ApplesPromotion implements Consumer<BasketItem> {

	private static final String NAME = "Apples", PROMOTION_TEXT_PREFIX = "Apples 10%", PROMOTION_TEXT = "off: %s";
	private static final BigDecimal PROMOTION_PERC = BigDecimal.valueOf(0.1);

	@Override
	public void accept(BasketItem item) {
		if (item.getName().equalsIgnoreCase(NAME)) {
			BigDecimal promotion = item.getValue().multiply(PROMOTION_PERC).negate();
			String promotionText = String.format("%s %s", PROMOTION_TEXT_PREFIX, String.format(PROMOTION_TEXT, PriceUtils.toString(promotion)));
			item.setPromotion( promotionText , promotion);
		}
	}

}
