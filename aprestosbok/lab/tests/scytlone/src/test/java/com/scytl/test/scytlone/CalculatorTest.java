package com.scytl.test.scytlone;

import org.junit.Assert;

import org.junit.Test;



/**
 * Unit test for simple App.
 */
public class CalculatorTest 
{


    /**
     * Rigourous Test :-)
     */
	@Test
    public void testEmptyString()
    {
    	Calculator _o = new CalculatorImpl();
    	
    	int _actual = _o.add("");
    	int _expected = 0;
    	
        Assert.assertEquals(_expected, _actual);
    }
	
	@Test
	public void testOneParam()
	{
		Calculator _o = new CalculatorImpl();
    	
    	int _actual = _o.add("4");
    	int _expected = 4;
    	
    	Assert.assertEquals(_expected, _actual);
	}
    
	@Test
	public void testTwoParam()
	{
		Calculator _o = new CalculatorImpl();
    	
    	int _actual = _o.add("3,4");
    	int _expected = 7;
    	
    	Assert.assertEquals(_expected, _actual);
	}
	
	@Test
	public void testMoreThanOneDelimiter()
	{
		Calculator _o = new CalculatorImpl();
    	
    	int _actual = _o.add("1\n2,3");
    	int _expected = 6;
    	
    	Assert.assertEquals(_expected, _actual);
	}
	
	@Test
	public void testDelimiterSetupCase()
	{
		Calculator _o = new CalculatorImpl();
    	
    	int _actual = _o.add("//#\n1#2#3");
    	int _expected = 6;
    	
    	Assert.assertEquals(_expected, _actual);
	}
	
	@Test
	public void testDelimiterWithMoreCharsSetupCase()
	{
		Calculator _o = new CalculatorImpl();
    	
    	int _actual = _o.add("//#!\n1#!2#!3");
    	int _expected = 6;
    	
    	Assert.assertEquals(_expected, _actual);
	}
    
}
