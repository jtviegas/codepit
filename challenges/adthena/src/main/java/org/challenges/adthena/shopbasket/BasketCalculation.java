package org.challenges.adthena.shopbasket;

import java.util.Arrays;

import org.challenges.adthena.shopbasket.model.Basket;
import org.challenges.adthena.shopbasket.model.BasketHelper;
import org.challenges.adthena.shopbasket.services.PriceEngine;
import org.challenges.adthena.shopbasket.services.promotions.PromotionEngine;

public class BasketCalculation {

	private final PriceEngine priceEngine;
	private final PromotionEngine promotionEngine;

	public BasketCalculation(final PriceEngine priceEngine, final PromotionEngine promotionEngine) {
		this.priceEngine = priceEngine;
		this.promotionEngine = promotionEngine;
	}

	public Basket calculate(final String... items) {
		final Basket basket = BasketHelper.create();
		Arrays.stream(items).map(priceEngine).map(promotionEngine).forEach(o -> basket.addItem(o));
		promotionEngine.reset();
		return basket;
	}

}
