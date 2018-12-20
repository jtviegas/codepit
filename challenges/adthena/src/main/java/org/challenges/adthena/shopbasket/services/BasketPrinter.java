package org.challenges.adthena.shopbasket.services;

import java.io.OutputStream;

import org.challenges.adthena.shopbasket.model.Basket;

public interface BasketPrinter {
	void print(Basket basket, OutputStream output);

	String print(Basket basket);
}
