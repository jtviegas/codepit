package org.challenges.adthena.shopbasket;

import org.challenges.adthena.shopbasket.model.Basket;
import org.challenges.adthena.shopbasket.services.BasketPrinter;
import org.challenges.adthena.shopbasket.services.PriceEngine;
import org.challenges.adthena.shopbasket.services.PromotionEngine;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BootTests {

	/*
	 * private TestContextManager testContextManager = new
	 * TestContextManager(getClass());
	 */

	@Autowired
	private PriceEngine priceEngine;
	@Autowired
	private PromotionEngine promotionEngine;
	@Autowired
	private BasketPrinter printer;

	/*
	 * @Before public void setUp() throws Exception {
	 * this.testContextManager.prepareTestInstance(this); }
	 */

	@Test
	public void initial_example() {
		String expected = "Subtotal: £3.10" + System.getProperty("line.separator") + "Apples 10% off: ­10p"
				+ System.getProperty("line.separator") + "Total: £3.00";
		BasketCalculation calculation = new BasketCalculation(priceEngine, promotionEngine);
		Basket basket = calculation.calculate(new String[] { "Apples", "Milk", "Bread" });
		Assert.assertEquals(expected, printer.print(basket));
	}

}
