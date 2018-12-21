package org.challenges.adthena.shopbasket;

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
public class BootTests {

	@Autowired
	private PriceEngine priceEngine;
	@Autowired
	private PromotionEngine promotionEngine;
	@Autowired
	private BasketPrinter printer;


	@Test
	public void initial_example() {
		String expected = "Subtotal: £3.10" + System.getProperty("line.separator") + "Apples 10% off: ­10p"
				+ System.getProperty("line.separator") + "Total: £3.00";
		BasketCalculation calculation = new BasketCalculation(priceEngine, promotionEngine);
		Basket basket = calculation.calculate(new String[] { "Apples", "Milk", "Bread" });
		Assert.assertEquals(expected, printer.print(basket));
	}

}
