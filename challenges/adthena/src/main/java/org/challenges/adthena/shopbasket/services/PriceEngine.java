package org.challenges.adthena.shopbasket.services;

import java.util.function.Function;

import org.challenges.adthena.shopbasket.model.BasketItem;

public interface PriceEngine extends Function<String, BasketItem> {

}
