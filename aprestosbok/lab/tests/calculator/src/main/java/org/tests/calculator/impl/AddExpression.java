package org.tests.calculator.impl;

import org.tests.calculator.interfaces.NonTerminalExpression;

public class AddExpression extends BasicExpression implements NonTerminalExpression 
{
	public AddExpression() {}
	
	public AddExpression(int _operand) 
	{
		super(_operand);
	}

	public int evaluate(int _val) 
	{
		if(null != next)
			return next.evaluate(_val + this.operand);
		else
			return _val + this.operand;
	}

}
