package org.challenges.adthena.shopbasket.services;

import java.io.OutputStream;

import org.challenges.adthena.shopbasket.exceptions.AppException;
import org.challenges.adthena.shopbasket.model.Basket;

public interface BasketPrinter {
	void print(Basket basket, OutputStream output) throws AppException;

	String print(Basket basket);
}
