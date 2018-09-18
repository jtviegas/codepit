package org.aprestos.labs.challenges.klarna.phaone.two;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Challenge {

	public static String numberToOrdinal(Integer number) {

		if (0 == number.intValue())
			return "0";

		int m = number.intValue() % 100;
		String suffix = null;

		if (m >= 10 & 20 > m) {
			suffix = "th";
		} else {
			m = number.intValue() % 10;
			switch (m) {
			case 1:
				suffix = "st";
				break;
			case 2:
				suffix = "nd";
				break;
			case 3:
				suffix = "rd";
				break;
			default:
				suffix = "th";

			}
		}
		return String.format("%d%s", number.intValue(), suffix);
	}

	@Test
	public void shouldHandleSingleDigits() {
		assertEquals("1st", Challenge.numberToOrdinal(1));
		assertEquals("2nd", Challenge.numberToOrdinal(2));
		assertEquals("3rd", Challenge.numberToOrdinal(3));
		assertEquals("4th", Challenge.numberToOrdinal(4));
		assertEquals("11th", Challenge.numberToOrdinal(11));
		assertEquals("21st", Challenge.numberToOrdinal(21));
		assertEquals("33rd", Challenge.numberToOrdinal(33));
		assertEquals("92nd", Challenge.numberToOrdinal(92));
		assertEquals("112th", Challenge.numberToOrdinal(112));
		assertEquals("3212th", Challenge.numberToOrdinal(3212));
		assertEquals("3222nd", Challenge.numberToOrdinal(3222));
	}

}
