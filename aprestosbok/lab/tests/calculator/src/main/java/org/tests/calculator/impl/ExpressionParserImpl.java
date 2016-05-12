package org.tests.calculator.impl;

import org.tests.calculator.interfaces.Expression;
import org.tests.calculator.interfaces.ExpressionParser;
import org.tests.calculator.misc.Operators;

public class ExpressionParserImpl implements ExpressionParser 
{
	
	public ExpressionParserImpl(){}

	public Expression parse(String _expression) 
	{
		String [] _operands = _expression.split(" ");
		Expression _result = solve(_operands[0]);
		_result.setOperand(Integer.parseInt(_operands[1].trim()));
		
		return _result;
	}
	
	private Expression solve(String _l)
	{
		Expression _x =null;
		
		if(_l.trim().toLowerCase().equals(Operators.add.toString()))
			_x = new AddExpression();
		
		if(_l.trim().toLowerCase().equals(Operators.divide.toString()))
			_x = new DivideExpression();
		
		if(_l.trim().toLowerCase().equals(Operators.multiply.toString()))
			_x = new MultiplyExpression();
		
		if(_l.trim().toLowerCase().equals(Operators.remainder.toString()))
			_x = new RemainderExpression();
		
		if(_l.trim().toLowerCase().equals(Operators.subtract.toString()))
			_x = new SubtractExpression();
		
		if(_l.trim().toLowerCase().equals(Operators.apply.toString()))
			_x = new ApplyExpression();
		
		
		return _x;
	}

}
