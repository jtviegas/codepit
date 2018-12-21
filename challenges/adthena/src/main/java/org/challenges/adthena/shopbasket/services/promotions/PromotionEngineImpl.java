package org.challenges.adthena.shopbasket.services.promotions;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.challenges.adthena.shopbasket.model.BasketItem;
import org.springframework.stereotype.Service;

/**
 * 
 * this promotion engine would normally in production code implement some rule
 * engine to properly define the promotion rules that would not have to be
 * implemented in code. Open-Closed principle.
 * 
 * This is a dumb one just for the sake of brevity.
 *
 */
@Service
public class PromotionEngineImpl implements PromotionEngine {

	List<Consumer<BasketItem>> promotions;

	public PromotionEngineImpl() {
		promotions = Arrays.asList(new ApplesPromotion(), new SoupPromotion());
	}

	@Override
	public BasketItem apply(BasketItem item) {
		promotions.forEach(a -> a.accept(item));
		return item;
	}

}
