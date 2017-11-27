package org.exercises.jtamv.commons;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AssortedTest {

    private Assorted o;
    @Before
    public void setUp() throws Exception {
	o = new Assorted();
    }

    @Test
    public void test() {
	
	int[] b = new int[]{1,2,5,9,10};
	int[] a = new int[]{2,3,4,8,10,12};
	int[] expected = new int[]{1,3,4,5,8,9,12};
	
	Assert.assertArrayEquals(expected, o.xor1(a,b));

    }
    
    @Test
    public void test2() {
	
	int[] b = new int[]{9,10,5,1,2};
	int[] a = new int[]{8,2,10,3,12,4};
	int[] expected = new int[]{1,3,4,5,8,9,12};
	
	Assert.assertArrayEquals(expected, o.xor2(a,b));

    }


}
