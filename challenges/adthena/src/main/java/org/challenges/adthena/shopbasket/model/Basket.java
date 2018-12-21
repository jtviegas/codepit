package org.challenges.adthena.shopbasket.model;

import java.math.BigDecimal;
import java.util.List;

public interface Basket {

	void addItem(BasketItem item);

	BigDecimal getSubtotal();

	BigDecimal getTotal();

	List<String> getPromotionDetails();

}
