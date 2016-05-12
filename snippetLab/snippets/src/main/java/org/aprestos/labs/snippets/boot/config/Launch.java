package org.aprestos.labs.snippets.boot.config;

import org.aprestos.labs.snippets.interfaces.Mode;
import org.aprestos.labs.snippets.interfaces.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Hello world!
 *
 */
@Component
public class Launch 
{
	@Autowired
	@Qualifier("snippet")
	private Snippet snippet;
	
	@Autowired
	@Qualifier("mode")
	Mode mode;
    
    public Launch(){}
    
    public void go(){
    	try {
			snippet.go();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	
    
    
    
}
