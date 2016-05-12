package org.tests.calculator.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.tests.calculator.exceptions.CalculatorException;
import org.tests.calculator.exceptions.ParsingException;
import org.tests.calculator.interfaces.Calculator;
import org.tests.calculator.interfaces.Expression;
import org.tests.calculator.interfaces.ExpressionParser;
import org.tests.calculator.interfaces.NonTerminalExpression;
import org.tests.calculator.interfaces.TerminalExpression;

public class StreamExpressionsCalculator implements Calculator 
{
	private TerminalExpression expression;
	private InputStream is;
	
	public StreamExpressionsCalculator(){}
	
	public StreamExpressionsCalculator(InputStream _is)
	{
		this.is = _is;
	}
	
	private void parseExpressions() throws ParsingException
	{
		String _line = null;
		NonTerminalExpression _nte = null;
		Expression _expression = null;
		ExpressionParser _parser = new ExpressionParserImpl();
		LineNumberReader _reader = null;
		
		try {
			
			_reader = new LineNumberReader(new InputStreamReader(is));
			this.expression = null;
			
			while(_reader.ready() && null != (_line = _reader.readLine()) && 0 < _line.trim().length())
			{
				_expression = _parser.parse(_line);
				
				if(_expression instanceof NonTerminalExpression)
				{
					if(null == _nte)
						_nte =(NonTerminalExpression)_expression;
					else
						_nte.addExpression((NonTerminalExpression)_expression);
				}
				
				if(_expression instanceof TerminalExpression)
				{
					this.expression = (TerminalExpression)_expression;
					this.expression.addExpression(_nte);
				}
			}
			
			if(null == this.expression)
				throw new ParsingException("no Terminal expression provided! You should at least provide an 'apply' expression as input.");
			
		} 
		catch (Exception e) 
		{
			throw new ParsingException(e);
		}
		finally
		{
			if(null != _reader)
				try { _reader.close(); } catch (IOException e) { e.printStackTrace(); }
			
			_reader = null;
		}
		
	}
	
	
	public int evaluate() throws CalculatorException
	{
			parseExpressions();
			return expression.evaluate();
	}

}
