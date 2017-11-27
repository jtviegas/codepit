package org.exercises.jtamv.exercises;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ExerciseTest {

    private Exercise o;
    @Before
    public void setUp() throws Exception {
	o = new Exercise();
    }
    
    public void test5() {
	
	int[] input = {1,3,46,1,3,9};
	long K = 47;
	int expected = 1;
	assertEquals(expected, o.nofP(input, K));
    }


    //@Test
    public void test() {
	
	String expected = "     #" + System.lineSeparator() +
		"    ##" + System.lineSeparator() +
		"   ###" + System.lineSeparator() +
		"  ####" + System.lineSeparator() +
		" #####" + System.lineSeparator() +
		"######" ;
	assertEquals(expected, o.doit(6));
    }
    
    //@Test
    public void test2() {
	
	String[] input = {"{}[]()", "{[}]}"};
	String[] expected = {"YES", "NO"};
	assertEquals(expected, o.doit2(input));
    }
    
    
    //@Test
    public void test3() {
	
	int[] input = {6,6,3,9,3,5,1};
	long K = 12;
	int expected = 2;
	assertEquals(expected, o.nofP(input, K));
    }
    
   //@Test
    public void test4() {
	
	int[] input = {1,3,46,1,3,9};
	long K = 47;
	int expected = 1;
	assertEquals(expected, o.nofP(input, K));
    }

}
