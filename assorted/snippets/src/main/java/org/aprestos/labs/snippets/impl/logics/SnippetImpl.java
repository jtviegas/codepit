package org.aprestos.labs.snippets.impl.logics;

import java.net.URI;

import org.springframework.stereotype.Component;

/*@Component("snippet")*/
public class SnippetImpl implements org.aprestos.labs.snippets.interfaces.Snippet 
{
	static long lastMbeanPoll = 0;
	static final long MBEAN_POLL_INTERVAL = 5000;

	public SnippetImpl(){
		super();
	}
	
	public void go() throws Exception {
		
		for (int i = 0 ; i < 99; i++) {
			
			long now = System.currentTimeMillis();
			
		
				if (1 > lastMbeanPoll || MBEAN_POLL_INTERVAL < (now - lastMbeanPoll)) {
					lastMbeanPoll = now;
					System.out.println("MBEAN call");
				} else {
					System.out.printf("last mean call: %d, diff: %d\n", lastMbeanPoll, (now - lastMbeanPoll));
				}
	
			Thread.sleep(500);
		}
		
	}
	
}
