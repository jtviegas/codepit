package org.challenges.adthena.shopbasket.model;

import java.math.BigDecimal;
import java.util.Set;

public interface Basket {

	void addItem(BasketItem item);

	BigDecimal getSubtotal();

	BigDecimal getTotal();

	Set<String> getPromotionDetails();

}
