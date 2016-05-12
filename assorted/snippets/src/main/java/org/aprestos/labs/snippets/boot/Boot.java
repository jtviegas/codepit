package org.aprestos.labs.snippets.boot;

import org.aprestos.labs.snippets.boot.config.Context;
import org.aprestos.labs.snippets.boot.config.Launch;

/**
 *
 *
 */
public class Boot 
{
	
    public static void main( String[] args )
    {
    	//Context is a singleton providing the spring application context for everybody
    	Launch launcher = Context.getInstance().getApplicationContext().getBean(Launch.class);
    	launcher.go();
    }
    
    
}
