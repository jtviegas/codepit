package org.tests.calculator;



import junit.framework.Assert;

import org.junit.Test;
import org.tests.calculator.exceptions.CalculatorException;
import org.tests.calculator.exceptions.ParsingException;
import org.tests.calculator.impl.StreamExpressionsCalculator;
import org.tests.calculator.interfaces.Calculator;

/**
 * Unit test for simple App.
 */
public class AppTest 
{


	@Test
    public void testOne() throws CalculatorException
    {
    	Calculator _c = new StreamExpressionsCalculator(AppTest.class.getResourceAsStream("/one.data"));
    	int _expected = 15;
    	Assert.assertEquals(_expected, _c.evaluate());

    }
	
	@Test
    public void testTwo() throws CalculatorException
    {
    	Calculator _c = new StreamExpressionsCalculator(AppTest.class.getResourceAsStream("/two.data"));
    	int _expected = 45;
    	Assert.assertEquals(_expected, _c.evaluate());

    }
	
	@Test
    public void testThree() throws CalculatorException
    {
    	Calculator _c = new StreamExpressionsCalculator(AppTest.class.getResourceAsStream("/three.data"));
    	int _expected = 1;
    	Assert.assertEquals(_expected, _c.evaluate());

    }
	
	@Test(expected=ParsingException.class)
    public void testFour() throws CalculatorException
    {
		Calculator _c = new StreamExpressionsCalculator(AppTest.class.getResourceAsStream("/four.data"));
    	int _expected = 15;
    	Assert.assertEquals(_expected, _c.evaluate());
        
    }
	
	@Test(expected=ParsingException.class)
    public void testFive() throws CalculatorException
    {
		Calculator _c = new StreamExpressionsCalculator(AppTest.class.getResourceAsStream("/five.data"));
    	int _expected = 15;
    	Assert.assertEquals(_expected, _c.evaluate());
        
    }
}
