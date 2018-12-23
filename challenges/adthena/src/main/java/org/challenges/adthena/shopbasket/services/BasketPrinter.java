package org.challenges.adthena.shopbasket.services;

import java.io.OutputStream;

import org.challenges.adthena.shopbasket.exceptions.AppException;
import org.challenges.adthena.shopbasket.model.Basket;

/**
 * BasketPrinter implements a specific way of printing the basket information.
 * 
 * @author jtviegas
 *
 */
public interface BasketPrinter {

	void print(Basket basket, OutputStream output) throws AppException;

	String print(Basket basket);
}
