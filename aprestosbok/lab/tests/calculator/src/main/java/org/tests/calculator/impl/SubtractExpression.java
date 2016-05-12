package org.tests.calculator.impl;

import org.tests.calculator.interfaces.NonTerminalExpression;

public class SubtractExpression extends BasicExpression implements NonTerminalExpression 
{
	public SubtractExpression() {}
	
	public SubtractExpression(int _operand) 
	{
		super(_operand);
	}

	public int evaluate(int _val) 
	{
		if(null != next)
			return next.evaluate(_val - this.operand);
		else
			return _val - this.operand;
	}

}
