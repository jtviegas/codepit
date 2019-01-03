package org.challenges.adthena.shopbasket.services;

import java.io.IOException;
import java.io.OutputStream;

import org.challenges.adthena.shopbasket.exceptions.AppException;
import org.challenges.adthena.shopbasket.model.Basket;
import org.challenges.adthena.shopbasket.model.PriceUtils;
import org.springframework.stereotype.Service;

@Service
public class BasketPrinterImpl implements BasketPrinter {

	private static final String SUBTOTAL_PATTERN = "Subtotal: %s", NO_OFFERS = "(No offers available)",
			TOTAL_PATERN = "Total: %s", LINE_SEP_PROP = "line.separator";

	@Override
	public void print(Basket basket, OutputStream output) throws AppException {
		try {
			output.write(print(basket).getBytes());
		} catch (IOException e) {
			throw new AppException("! problems writing on the output stream !", e);
		}
	}

	@Override
	public String print(Basket basket) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(String.format(SUBTOTAL_PATTERN, PriceUtils.toString(basket.getSubtotal())))
				.append(System.getProperty(LINE_SEP_PROP));

		if (basket.getPromotionDetails().isEmpty())
			buffer.append(NO_OFFERS);
		else
			buffer.append(String.join(System.getProperty(LINE_SEP_PROP), basket.getPromotionDetails()));

		buffer.append(System.getProperty(LINE_SEP_PROP))
				.append(String.format(TOTAL_PATERN, PriceUtils.toString(basket.getTotal())))
				.append(System.getProperty(LINE_SEP_PROP));

		return buffer.toString();
	}

}
