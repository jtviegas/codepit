package org.challenges.adthena.shopbasket.services.promotions;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.challenges.adthena.shopbasket.model.BasketItem;
import org.challenges.adthena.shopbasket.model.PriceUtils;

public class SoupPromotion implements Promotion<BasketItem> {

	private static final String NAME = "Soup", TARGET_NAME = "Bread", PROMO_TXT = "off on 1 Bread:",
			PROMO_TXT_PREFIX = "Buy 2 Soups and get 50%";
	private static final BigDecimal PROMO_PERC = BigDecimal.valueOf(0.5);
	private int soupCount;
	private int promotionsToApply;
	final private Set<BasketItem> promotionTargets = new HashSet<BasketItem>();

	@Override
	public void accept(BasketItem item) {

		if (item.getName().equalsIgnoreCase(TARGET_NAME))
			promotionTargets.add(item);
		else if (item.getName().equalsIgnoreCase(NAME)) {
			soupCount++;
			if (0 == soupCount % 2) {
				promotionsToApply = soupCount / 2;
				soupCount = 0;
			}
		}

		if (0 < promotionsToApply && !promotionTargets.isEmpty())
			applyPromotions();

	}

	private void applyPromotions() {

		Iterator<BasketItem> iterator = promotionTargets.iterator();
		do {
			BasketItem item = iterator.next();
			BigDecimal promotion = item.getValue().multiply(PROMO_PERC).negate();
			item.setPromotion(
					String.format("%s %s %s", PROMO_TXT_PREFIX, PROMO_TXT, PriceUtils.toString(promotion)),
					promotion);
			promotionsToApply--;
			iterator.remove();
		} while (0 < promotionsToApply && iterator.hasNext());

	}

	@Override
	public void reset() {
		soupCount = 0;
		promotionsToApply = 0;
		promotionTargets.clear();
	}

}
