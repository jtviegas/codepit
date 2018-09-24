package org.aprestos.labs.challenges.babylon;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Challenge {

	@Test
	public void test() throws Exception {
		Assert.assertEquals(true, checkExpressionBraces("a"));
		Assert.assertEquals(false, checkExpressionBraces("("));
		Assert.assertEquals(true, checkExpressionBraces("aaa(s)"));
		Assert.assertEquals(false, checkExpressionBraces("(a[s)"));
		Assert.assertEquals(true, checkExpressionBraces("[ere]sasa"));
		Assert.assertEquals(false, checkExpressionBraces("[a[s]asa]"));
		Assert.assertEquals(true, checkExpressionBraces("{asdsad[dffd]sada}"));
		Assert.assertEquals(false, checkExpressionBraces("[sad{e}sa]"));
		Assert.assertEquals(true, checkExpressionBraces("[asss(sf[3]f)]"));
		Assert.assertEquals(true, checkExpressionBraces("()"));
		Assert.assertEquals(false, checkExpressionBraces(")"));
	}

	static boolean checkExpressionBraces(String expression) {

		boolean result = true;

		char[] string = expression.toCharArray();
		int l = 0, r = string.length;
		char openBrace = 0;
		Set<Character> previousBraces = new LinkedHashSet<Character>();

		if (1 == string.length) {
			return !isBrace(string[0]);
		}

		while (l < r) {

			if (0 == openBrace) {

				if (isBrace(string[l])) {

					if (isClosingBrace(string[l])) {
						result = false;
						break;
					}

					openBrace = string[l];

					if (!validNesting(previousBraces, openBrace)) {
						result = false;
						break;
					}

				}
				l++;

			} else {
				r--;
				if (isBrace(string[r])) {
					if (string[r] == getMatchingBrace(openBrace)) {
						previousBraces.add(openBrace);
						openBrace = 0;
					} else {
						result = false;
						break;
					}
				}

			}

		}

		result &= (openBrace == 0);

		return result;
	}

	static boolean isBrace(char c) {
		if (c == '[' || c == '(' || c == '{' || c == '}' || c == ')' || c == ']')
			return true;
		return false;
	}

	static boolean isClosingBrace(char c) {
		if (c == '}' || c == ')' || c == ']')
			return true;
		return false;
	}

	static char getMatchingBrace(char c) {
		char r = 0;
		switch (c) {
		case '[':
			r = ']';
			break;
		case '(':
			r = ')';
			break;
		case '{':
			r = '}';
			break;
		}
		return r;
	}

	static boolean validNesting(Set<Character> previous, char current) {

		boolean r = true;

		r &= !(!previous.isEmpty() && current == '{');

		boolean r2 = true;
		if (current == '[') {

			char prev = 0;
			int index = 0;
			for (Character c : previous) {
				if (0 == prev)
					prev = c;
				else {
					if (c == '[' && prev == '[') {
						r2 = false;
						break;
					} else {
						prev = c;
					}
				}
				if (index == (previous.size() - 1) && c == '[')
					r2 = false;

				index++;
			}
		}

		r &= r2;

		return r;
	}

}
