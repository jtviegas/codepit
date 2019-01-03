package org.challenges.adthena.shopbasket.services.promotions;

import java.math.BigDecimal;

import org.challenges.adthena.shopbasket.model.BasketItem;
import org.challenges.adthena.shopbasket.model.PriceUtils;

public class ApplesPromotion implements Promotion<BasketItem> {

	private static final String NAME = "Apples", PROMO_TXT_PREFIX = "Apples 10%", PROMO_TXT = "off: %s";
	private static final BigDecimal PROMO_PERC = BigDecimal.valueOf(0.1);

	@Override
	public void accept(BasketItem item) {
		if (item.getName().equalsIgnoreCase(NAME)) {
			BigDecimal promotion = item.getValue().multiply(PROMO_PERC).negate();
			String promotionText = String.format("%s %s", PROMO_TXT_PREFIX,
					String.format(PROMO_TXT, PriceUtils.toString(promotion)));
			item.setPromotion(promotionText, promotion);
		}
	}

	@Override
	public void reset() {
		// do nothing
	}

}
