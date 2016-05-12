package org.challenges.odobo;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.challenges.odobo.exceptions.ChallengeException;
import org.challenges.odobo.impl.ChallengeImpl;
import org.challenges.odobo.interfaces.Challenge;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	Challenge _o = new ChallengeImpl();
    	
    	try 
    	{
			_o.execute();
		} 
    	catch (ChallengeException e) 
		{
			System.out.println(ExceptionUtils.getFullStackTrace(e));
			System.exit(-1);
		}
        
        
    }
}
