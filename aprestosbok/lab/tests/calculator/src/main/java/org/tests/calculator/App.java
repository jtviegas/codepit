package org.tests.calculator;

import java.io.FileInputStream;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.tests.calculator.impl.StreamExpressionsCalculator;
import org.tests.calculator.interfaces.Calculator;


public class App 
{
	
	private static final String USAGE=" java -jar calculator-1.0-SNAPSHOT.jar <file> - should always provide the expressions file as the only argument !!!";
	
    public static void main( String[] args )
    {
    	if(args.length != 1)
    	{	
    		System.out.println(USAGE);
    		System.exit(-1);
    	}

    	try 
    	{
    		Calculator _c = new StreamExpressionsCalculator(new FileInputStream(args[0]));
    		System.out.println(_c.evaluate());
		} 
    	catch (Exception e) 
    	{
			System.out.println(ExceptionUtils.getFullStackTrace(e));
			System.exit(-1);
		}
    }
}
