package org.aprestos.labs.challenges.glencore;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BraceChecker {

	@Test
	public void testValid() {
		assertEquals(true, new BraceChecker().isValid("()"));
	}

	@Test
	public void testInvalid() {
		assertEquals(false, new BraceChecker().isValid("[(])"));
	}

	public boolean isValid2(String braces) {
		boolean result = true;
		Stack<Character> pile = new Stack<Character>();
		char now, before;
		for (int i = 0; i < braces.length(); i++) {
			now = braces.charAt(i);
			if (now == '(' || now == '[' || now == '{') {
				pile.push(now);
			} else if (now == ')' || now == ']' || now == '}') {
				if (pile.isEmpty()) {
					result = false;
				} else {
					before = pile.peek();
					if ((now == ')' && before == '(') || (now == ']' && before == '[')
							|| (now == '}' && before == '{')) {
						pile.pop();
					} else {
						result = false;
					}
				}
			}
		}
		if (!pile.isEmpty()) {
			result = false;
		}
		return result;
	}

	public boolean isValid(String braces) {
		boolean result = true;
		Stack<Character> pile = new Stack<Character>();

		return result;
	}

	boolean isOpening(Character character) {
		return character.equals('(') || character.equals('[') || character.equals('{');
	}

	Character closing(Character character) {
		Character result = null;
		switch (character) {
		case '{':
			result = '}';
			break;
		case '[':
			result = ']';
			break;
		case '(':
			result = ')';
			break;
		}
		return result;
	}

}
