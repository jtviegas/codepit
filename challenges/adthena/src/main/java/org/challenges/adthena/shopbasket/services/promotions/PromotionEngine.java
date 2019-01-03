package org.challenges.adthena.shopbasket.services.promotions;

import java.util.function.Function;

import org.challenges.adthena.shopbasket.model.BasketItem;

/**
 * PromotionEngine this promotion engine would normally, in production-ready
 * code, implement/load , for instance, some rule engine to properly define the
 * promotion rules that would not have to be implemented/hard-coded in here.
 * Open-Closed principle.
 * 
 * This is a dumb approach just for the sake of brevity.
 *
 */
public interface PromotionEngine extends Function<BasketItem, BasketItem> {

	void reset();

}
