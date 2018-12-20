package org.challenges.adthena.shopbasket.model;

public class BasketFactory {

	public static Basket create() {
		return new BasketImpl();
	}

	public static BasketItem createItem() {
		return new BasketItemImpl();
	}

}
