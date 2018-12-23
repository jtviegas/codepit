package org.challenges.adthena.shopbasket.services.promotions;

import java.util.Arrays;
import java.util.List;

import org.challenges.adthena.shopbasket.model.BasketItem;
import org.springframework.stereotype.Service;

@Service
public class PromotionEngineImpl implements PromotionEngine {

	private final List<Promotion<BasketItem>> promotions;

	public PromotionEngineImpl() {
		promotions = Arrays.asList(new ApplesPromotion(), new SoupPromotion());
	}

	@Override
	public BasketItem apply(BasketItem item) {
		promotions.forEach(a -> a.accept(item));
		return item;
	}

	@Override
	public void reset() {
		promotions.forEach(a -> a.reset());
	}

}
