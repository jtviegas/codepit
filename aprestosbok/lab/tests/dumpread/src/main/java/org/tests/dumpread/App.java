package org.tests.dumpread;

import java.io.FileInputStream;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final String USAGE=" java -jar dumpread.jar <file> - should always provide the csv file as the only argument !!!";
	
    public static void main( String[] args )
    {
    	
    	if(args.length != 1)
    	{	
    		System.out.println(USAGE);
    		System.exit(-1);
    	}

    	try 
    	{
    		ApplicationContext context = new ClassPathXmlApplicationContext("springconfig.xml");
        	
        	DumpReader dr = (DumpReader)context.getBean("dumpreader");
        	
			dr.readDumpIn(new FileInputStream(args[0]));
		} 
    	catch (Exception e) 
    	{
			System.out.println(ExceptionUtils.getFullStackTrace(e));
			System.exit(-1);
		}
    	
    }
}
