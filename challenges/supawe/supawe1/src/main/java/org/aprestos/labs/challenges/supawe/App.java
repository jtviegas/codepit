package org.aprestos.labs.challenges.supawe;

import java.math.BigInteger;

/**
 * Hello world!
 *
 */
public class App {

	private static final String INSUFFICIENT_ARGS = "insufficient arguments found: must provide two numeric arguments";
	private static final String WRONG_ARGS = "both arguments must have numeric format, must represent natural numbers";

	public static void main(String[] args) {

		if (2 != args.length) {
			System.out.println(INSUFFICIENT_ARGS);
			return;
		}

		try {
			BigInteger value = new App().doIt(args);
			System.out.println(value.toString());
		} catch (NumberFormatException nfe) {
			System.out.println(WRONG_ARGS);
		}

	}

	public BigInteger doIt(String[] args) {

		BigInteger a = new BigInteger(args[0]);
		BigInteger b = new BigInteger(args[1]);
		return a.add(b);
	}
}
