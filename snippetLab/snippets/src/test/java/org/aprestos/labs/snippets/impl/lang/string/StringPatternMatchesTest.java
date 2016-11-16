package org.aprestos.labs.snippets.impl.lang.string;


import org.junit.Assert;
import org.junit.Test;

public class StringPatternMatchesTest {


	
	@Test
	public void stringPatternMatches_one() {
		
		String[] patterns = {".*","Important.*", ".*Key"};
		String text = "ImportantResourceKey";
		
		for(String pattern:patterns){
		    Assert.assertTrue(text.matches(pattern));
		}
		
	}
	
	@Test
	public void stringPatternMatches_two() {
		
		String[] patterns = {"^[1]{1,3}.*","[a-zA-z].*[0-9]{1,3}$"};
		String[] texts = {"111res", "11res", "1res", "res1","res11", "res111"};
		String p = null, t = null;
		
		for(int i=0; i<patterns.length; i++){
		    p = patterns[i/3];
		    t = texts[i];
		    Assert.assertTrue(t.matches(p));
		}
		
	}




}
