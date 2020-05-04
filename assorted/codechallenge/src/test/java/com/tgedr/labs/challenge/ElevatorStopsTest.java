package com.tgedr.labs.challenge;

import com.tgedr.labs.challenge.assorted.ElevatorStops;
import org.junit.Assert;
import org.junit.Test;

public class ElevatorStopsTest {

    private int M=5;// floors 0-based
    private int X=2; // max elevator capacity
    private int[] A = {60, 80, 40}; //weights 
    private int[] B = {2, 3, 5}; //target floors
    private int Y = 200; //weigth limit
    
    @Test
    public void testOne(){
	Assert.assertTrue(5 == ElevatorStops.getNumOfStops(A, B, M, X, Y));
    }
    
    @Test
    public void testTwo(){
	 M = 3;// floors 0-based
	 X=5; // max elevator capacity
	 A = new int[]{40, 40, 100, 80, 20}; //weights 
	 B = new int[]{3,3,2,2,3}; //target floors
	 Y = 200; //weigth limit
	Assert.assertTrue(6 == ElevatorStops.getNumOfStops(A, B, M, X, Y)); 
	
    }
}
