package org.aprestos.labs.challenges.klarna.three;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class Calc {

	List<String> operations = Arrays.asList("-", "+", "*", "/");

	public double calculate(double op1, double op2, String operation) {
		double r = 0;
		switch (operation.trim()) {
		case "+":
			r = op1 + op2;
			break;
		case "-":
			r = op1 - op2;
			break;
		case "/":
			r = op1 / op2;
			break;
		default:
			r = op1 * op2;
		}
		return r;
	}

	public double evaluate(String expr) {

		if (null == expr || 1 > expr.length())
			return 0;

		Deque<Double> stack = new ArrayDeque<Double>();

		for (String s : expr.split(" ")) {
			if (operations.contains(s) && 1 < stack.size()) {
				double operand2 = stack.pop();
				double operand1 = stack.pop();
				stack.push(calculate(operand1, operand2, s));
			} else
				stack.push(Double.parseDouble(s));
		}

		return stack.pop().doubleValue();
	}

	private boolean isNumeric(String n) {
		boolean r = true;
		try {
			Double.parseDouble(n);
		} catch (NumberFormatException e) {
			r = false;
		}
		return r;
	}

}
