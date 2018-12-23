package org.challenges.adthena.shopbasket;

import org.challenges.adthena.shopbasket.exceptions.InputException;
import org.challenges.adthena.shopbasket.model.Basket;
import org.challenges.adthena.shopbasket.services.BasketPrinter;
import org.challenges.adthena.shopbasket.services.PriceEngine;
import org.challenges.adthena.shopbasket.services.promotions.PromotionEngine;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@Import(Config.class)
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BasketTests {

	@Autowired
	private PriceEngine priceEngine;
	@Autowired
	private PromotionEngine promotionEngine;
	@Autowired
	private BasketPrinter printer;

	@Test
	public void initialExample() {
		final String expected = "Subtotal: £3.10\nApples 10% off: -10p\nTotal: £3.00\n";
		final BasketCalculation calculation = new BasketCalculation(priceEngine, promotionEngine);
		Basket basket = calculation.calculate(new String[] { "Apples", "Milk", "Bread" });
		Assert.assertEquals(expected, printer.print(basket));
	}

	@Test
	public void anotherExample() {
		final String expected = "Subtotal: £4.70\nBuy 2 Soups and get 50% off on 1 Bread: -40p\nTotal: £4.30\n";
		final BasketCalculation calculation = new BasketCalculation(priceEngine, promotionEngine);
		Basket basket = calculation.calculate(new String[] { "Milk", "Bread", "Milk", "Soup", "Soup" });
		Assert.assertEquals(expected, printer.print(basket));
	}

	@Test
	public void noPromotions() {
		final String expected = "Subtotal: £3.40\n(No offers available)\nTotal: £3.40\n";
		final BasketCalculation calculation = new BasketCalculation(priceEngine, promotionEngine);
		Basket basket = calculation.calculate(new String[] { "Milk", "Bread", "Milk" });
		Assert.assertEquals(expected, printer.print(basket));
	}

	@Test(expected = InputException.class)
	public void unknownItems() {
		final BasketCalculation calculation = new BasketCalculation(priceEngine, promotionEngine);
		calculation.calculate(new String[] { "gugu" });
		Assert.fail();
	}

}
