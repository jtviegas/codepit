package org.challenges.adthena.shopbasket.services.promotions;

import java.util.function.Consumer;

public interface Promotion<T> extends Consumer<T> {
	void reset();
}
