package org.challenges.adthena.shopbasket.model;

import java.math.BigDecimal;

public interface BasketItem {

	void setValue(BigDecimal value);

	void setPromotion(String text, BigDecimal value);

	BigDecimal getValue();

	String getPromotionText();

	BigDecimal getPromotion();

}
