package org.aprestos.labs.challenges.klarna.phaone.three;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CalcTest {

	private Calc calc = new Calc();

	@Test
	public void shouldWorkForAnEmptyString() {
		assertEquals(0, calc.evaluate(""), 0);
	}

	@Test
	public void shouldParseNumbers() {
		assertEquals(3, calc.evaluate("1 2 3"), 0);
	}

	@Test
	public void shouldParseFloats() {
		assertEquals(3.5, calc.evaluate("1 2 3.5"), 0);
	}

	@Test
	public void shouldSupportAddition() {
		assertEquals(4, calc.evaluate("1 3 +"), 0);
	}

	@Test
	public void shouldSupportMultiplication() {
		assertEquals(3, calc.evaluate("1 3 *"), 0);
	}

	@Test
	public void shouldSupportSubtraction() {
		assertEquals(-2, calc.evaluate("1 3 -"), 0);
	}

	@Test
	public void shouldSupportDivision() {
		assertEquals(2, calc.evaluate("4 2 /"), 0);
	}

}
