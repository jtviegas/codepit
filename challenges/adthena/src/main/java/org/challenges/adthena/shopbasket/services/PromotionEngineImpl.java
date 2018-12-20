package org.challenges.adthena.shopbasket.services;

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

	public PromotionEngineImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public BasketItem apply(BasketItem t) {
		// TODO Auto-generated method stub
		return null;
	}

}
