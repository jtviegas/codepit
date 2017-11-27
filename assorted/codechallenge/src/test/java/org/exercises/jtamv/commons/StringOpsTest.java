package org.exercises.jtamv.commons;

import org.junit.Assert;
import org.junit.Test;

public class StringOpsTest {
    
    @Test
    public void testReverseString(){
	Assert.assertEquals("fox brown quick the", StringOps.reverseString("the quick brown fox"));
    }
    
    @Test
    public void testPrintStringWithWordsReversed(){
	Assert.assertEquals("ew tset sredoc",StringOps.printStringWithWordsReversed("we test coders")); 
	Assert.assertEquals("ew tset sredoc nelam",StringOps.printStringWithWordsReversed("we test coders malen"));
	Assert.assertEquals("ghfew",StringOps.printStringWithWordsReversed("wefhg"));
    }
    
    @Test
    public void testGetCeasarCypher(){
	Assert.assertEquals("Hello world!", StringOps.getCeasarCypher("Zwddg ogjdv!", 8));
    }
    
    @Test
    public void testFindDuplicateEntries(){
	String[] airports1 = { "Dublin", "Shannon", "Belfast", "London" };
	String[] airports2 = { "London", "Paris", "New York", "Dublin",
		"Venice" };
	Assert.assertEquals(2, StringOps.findDuplicateEntries(airports1, airports2).length);
    }

}
