package org.challenges.adthena.shopbasket.services;

import java.util.function.Function;

import org.challenges.adthena.shopbasket.model.BasketItem;

public interface PromotionEngine extends Function<BasketItem, BasketItem> {

}
