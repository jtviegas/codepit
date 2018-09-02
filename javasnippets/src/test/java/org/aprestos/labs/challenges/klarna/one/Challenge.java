package org.aprestos.labs.challenges.klarna.one;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Challenge {

	private static final char maskChar = '#';

	public static String maskify(String creditCardNumber) {

		if (null == creditCardNumber || 6 > creditCardNumber.length())
			return creditCardNumber;

		char[] mask = new char[creditCardNumber.length()];

		int upperBoundary = creditCardNumber.length() - 5;
		int lowerBoundary = 1;
		for (int i = creditCardNumber.length() - 1; i >= 0; i--) {
			char c = creditCardNumber.charAt(i);
			if (i >= lowerBoundary && i <= upperBoundary && Character.isDigit(c))
				mask[i] = maskChar;
			else
				mask[i] = c;
		}

		return new String(mask);
	}

	@Test
	public void shouldMaskAnother() {
		assertEquals("A#######BCDEFG89HI", maskify("A1234567BCDEFG89HI"));
	}

	@Test
	public void shouldHandleNull() {
		assertEquals(null, maskify(null));
	}

	@Test
	public void shouldMaskDigitsForBasicCreditCards() {
		assertEquals("5###########0694", maskify("5512103073210694"));
	}

	@Test
	public void shouldNotMaskDigitsForShortCreditCards2() {
		assertEquals("54321", maskify("54321"));
	}

	@Test
	public void shouldMaskDigitsForBasicCreditCards2() {
		assertEquals("4###-####-####-5616", maskify("4556-3646-0793-5616"));
	}

	@Test
	public void shouldMaskDigitsForSmallCreditCards() {
		assertEquals("6######5616", maskify("64607935616"));
	}

	@Test
	public void shouldNotMaskNonDigits() {
		assertEquals("ABCD-EFGH-IJKLM-NOPQ", maskify("ABCD-EFGH-IJKLM-NOPQ"));
	}

	@Test
	public void shouldHandleEmptyStringMasking() {
		assertEquals("", maskify(""));
	}

}
