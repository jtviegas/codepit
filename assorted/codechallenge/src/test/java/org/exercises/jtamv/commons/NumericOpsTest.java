package org.exercises.jtamv.commons;

import org.junit.Assert;
import org.junit.Test;

public class NumericOpsTest {
    
    @Test
    public void testBalancedArray(){
	int[] i = { -1, 3, -4, 5, 1, -6, 2, 1};
	Assert.assertTrue(1 == NumericOps.balanceArray(i)); 
	
    }
    
    @Test
    public void testMinimum(){
	int[] i = {1, 2, 3, 42, 1, -10};
	Assert.assertTrue(-10 == NumericOps.getMinimum(i)); 
    }
    
    @Test
    public void testBiggestSequenceOfAscendingValues(){
	int[] i = {2, 2, 2, 2, 1, 2, -1, 2, 1, 3 };
	Assert.assertTrue(4 == NumericOps.biggestSequenceOfAscendingValues(i)); 
	
    }
    
    @Test
    public void testMinimumOperationsToNumber(){
	Assert.assertTrue(6 == NumericOps.minimumOperationsToNumber(18)); 
    }
    
    @Test
    public void testIsOneSwapEnough2beSortedAscending(){
	
	int[] A = {1, 5, 3, 3, 7};
	Assert.assertEquals(true,NumericOps.isOneSwapEnough2beSortedAscending(A));
	
	A = new int[]{1,3,5,3,4};
	Assert.assertEquals(false,NumericOps.isOneSwapEnough2beSortedAscending(A));
	
	A = new int[]{1,3,5};
	Assert.assertEquals(true,NumericOps.isOneSwapEnough2beSortedAscending(A));
	
	A = new int[]{1};
	Assert.assertEquals(true,NumericOps.isOneSwapEnough2beSortedAscending(A));
	
	A = new int[]{1,1,1,1,1,0};
	Assert.assertEquals(true,NumericOps.isOneSwapEnough2beSortedAscending(A));
	
	A = new int[]{1,0};
	Assert.assertEquals(true,NumericOps.isOneSwapEnough2beSortedAscending(A));
	
	A = new int[]{1,5,3,3,4};
	Assert.assertEquals(false,NumericOps.isOneSwapEnough2beSortedAscending(A));
	
	A = new int[]{1,5,4,4,4};
	Assert.assertEquals(true,NumericOps.isOneSwapEnough2beSortedAscending(A));
	
	A = new int[]{1,5,3,4,3};
	Assert.assertEquals(true,NumericOps.isOneSwapEnough2beSortedAscending(A));
	
    }

}
