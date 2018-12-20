package org.challenges.adthena.shopbasket.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.challenges.adthena.shopbasket.exceptions.InputException;
import org.challenges.adthena.shopbasket.model.BasketFactory;
import org.challenges.adthena.shopbasket.model.BasketItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PriceEngineImpl implements PriceEngine {

	private Map<String, BigDecimal> items = new HashMap<String, BigDecimal>();

	public PriceEngineImpl(@Value("${shopbasket.items.name}") String[] itemNames,
			@Value("${shopbasket.items.price}") String[] itemPrices) {
		init(itemNames, itemPrices);
	}

	@Override
	public BasketItem apply(String arg0) {
		BasketItem result = null;
		BigDecimal price = null;

		if (null != (price = items.get(arg0))) {
			result = BasketFactory.createItem();
			result.setValue(price);
		} else
			throw new InputException(String.format("item not found in store: %d", arg0));

		return result;
	}

	private void init(String[] itemNames, String[] itemPrices) {
		try {
			for (int i = 0; i < itemNames.length; i++)
				items.put(itemNames[i].trim(), BigDecimal.valueOf(Double.parseDouble(itemPrices[i].trim())));
		} catch (Exception e) {
			throw new RuntimeException("couldn't load items");
		}

	}

}
