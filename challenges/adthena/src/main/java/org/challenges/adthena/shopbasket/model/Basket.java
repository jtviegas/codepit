package org.challenges.adthena.shopbasket.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * the shopping basket with all the items and finally with all pricing and
 * promotion info associated with it
 * 
 * @author jtviegas
 *
 */
public interface Basket {

	void addItem(BasketItem item);

	BigDecimal getSubtotal();

	BigDecimal getTotal();

	List<String> getPromotionDetails();

}
