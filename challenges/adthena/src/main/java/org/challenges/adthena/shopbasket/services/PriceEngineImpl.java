package org.challenges.adthena.shopbasket.services;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.challenges.adthena.shopbasket.exceptions.ConfigException;
import org.challenges.adthena.shopbasket.exceptions.InputException;
import org.challenges.adthena.shopbasket.model.BasketHelper;
import org.challenges.adthena.shopbasket.model.BasketItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PriceEngineImpl implements PriceEngine {

	final private Map<String, BigDecimal> items = new ConcurrentHashMap<String, BigDecimal>();

	public PriceEngineImpl(@Value("${shopbasket.items.name}") String[] itemNames,
			@Value("${shopbasket.items.price}") String[] itemPrices) {
		init(itemNames, itemPrices);
	}

	@Override
	public BasketItem apply(String name) {
		BasketItem result = null;
		BigDecimal price = null;

		if (null != (price = items.get(name))) {
			result = BasketHelper.createItem();
			result.setValue(price);
			result.setName(name);
		} else
			throw new InputException(String.format("item not found in store: %s", name));

		return result;
	}

	private void init(String[] itemNames, String[] itemPrices) {
		try {
			for (int i = 0; i < itemNames.length; i++)
				items.put(itemNames[i].trim(), BigDecimal.valueOf(Double.parseDouble(itemPrices[i].trim())));
		} catch (Exception e) {
			throw new ConfigException("couldn't load items", e);
		}

	}

}
