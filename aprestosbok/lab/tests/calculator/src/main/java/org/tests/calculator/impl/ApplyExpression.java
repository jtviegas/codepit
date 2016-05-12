package org.tests.calculator.impl;

import org.tests.calculator.interfaces.TerminalExpression;

public class ApplyExpression extends BasicExpression implements TerminalExpression 
{

	public ApplyExpression(){}

	public ApplyExpression(int _operand) 
	{
		super(_operand);
	}

	public int evaluate() 
	{
		
		if(null != next)
			return next.evaluate(this.operand);
		else
			return this.operand;
	}

}
