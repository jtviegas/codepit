package org.tests.calculator.impl;

import org.tests.calculator.interfaces.Expression;
import org.tests.calculator.interfaces.NonTerminalExpression;

public class BasicExpression implements Expression 
{

	protected NonTerminalExpression next = null;
	protected int operand;
	
	public BasicExpression() {}
	public BasicExpression(int _operand) 
	{
		this.operand = _operand;
	}

	public void addExpression(NonTerminalExpression _expression) 
	{
		if(null == this.next)
			this.next = _expression;
		else
			this.next.addExpression(_expression);
	}

	public void setOperand(int _operand) 
	{
		this.operand = _operand;
	}

}
