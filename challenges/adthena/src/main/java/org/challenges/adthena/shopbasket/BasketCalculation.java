package org.challenges.adthena.shopbasket;

import java.util.Arrays;

import org.challenges.adthena.shopbasket.model.Basket;
import org.challenges.adthena.shopbasket.model.BasketFactory;
import org.challenges.adthena.shopbasket.services.PriceEngine;
import org.challenges.adthena.shopbasket.services.promotions.PromotionEngine;

public class BasketCalculation {

	private final PriceEngine priceEngine;
	private final PromotionEngine promotionEngine;

	public BasketCalculation(PriceEngine priceEngine, PromotionEngine promotionEngine) {
		this.priceEngine = priceEngine;
		this.promotionEngine = promotionEngine;
	}

	public Basket calculate(String[] items) {
		Basket basket = BasketFactory.create();
		Arrays.stream(items).map(priceEngine).map(promotionEngine).forEach(basket::addItem);
		return basket;
	}

}
