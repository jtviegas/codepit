package org.challenges.adthena.shopbasket.services;

import java.util.function.Function;

import org.challenges.adthena.shopbasket.model.BasketItem;

/**
 * PriceEngine should build map describing the goods. The map will have the name
 * of the good as key and its price as value. It will then, once provided with a
 * basket item name input, load that same item from the map and stream it.
 * 
 * @author jtviegas
 *
 */
public interface PriceEngine extends Function<String, BasketItem> {

}
