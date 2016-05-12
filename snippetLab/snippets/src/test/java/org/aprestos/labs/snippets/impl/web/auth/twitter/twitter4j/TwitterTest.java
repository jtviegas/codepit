package org.aprestos.labs.snippets.impl.web.auth.twitter.twitter4j;

import java.util.List;

import org.junit.Test;

import twitter4j.Status;

/**
 * Unit test for simple App.
 */
public class TwitterTest {

	@Test
	public void twitterTests() throws Exception {
		
		
		;
		List<Status> statuses = TwitterEngine.getInstance().twitter().getHomeTimeline();
	    System.out.println("Showing home timeline.");
	    for (Status status : statuses) 
	        System.out.println(status.getUser().getName() + ":" +
	                           status.getText());
	        
	}

}
