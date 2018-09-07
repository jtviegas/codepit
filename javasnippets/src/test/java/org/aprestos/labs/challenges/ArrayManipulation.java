package org.aprestos.labs.challenges;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArrayManipulation {

	@Test
	public void test() throws Exception {

		Assert.assertEquals(200l, arrayManipulation(5, Utils.LinesToIntMatrix(
		    "1 2 100£2 5 100£3 4 100".replaceAll("£", System.getProperty("line.separator"))
		    )));
		
		Assert.assertEquals(10l, arrayManipulation(10, Utils.LinesToIntMatrix(
        "1 5 3£4 8 7£6 9 1".replaceAll("£", System.getProperty("line.separator"))
        )));
	}

	
	
	
	static long[][] intersectAndReplace(long[] a, long[][] intersections){
	  
	  for( int i=0; i< intersections.length && null != intersections[i]; i++ ) {
	    long[] intersection = intersections[i];
	    
	    long[] common = null;
	    if( null != (common = Utils.intersect(a, intersection) ) ) {
	      
	    }
	    else {
	      Utils.expand(a, add)
	    }
	      

	      
	  }
	  
	}

	
	static long arrayManipulation(int n, int[][] queries) {
    long r = 0;
    
    long[][] intersections = new long[n][3];
    
    for( int i=0; i<queries.length; i++ ) {
      int startIndex = queries[i][0] - 1;
      int endIndex = queries[i][1] - 1;
      long value = queries[i][2];
      intersections = intersectAndReplace(new long[]{startIndex, endIndex, value}, intersections );
    }
    
    long[] values = new long[n];
    for( int i=0; i<intersections.length; i++ ) {
      int startIndex = (int) intersections[i][0];
      int endIndex = (int) intersections[i][1];
      long val = intersections[i][2];
      
      for(int j=startIndex; j<=endIndex; j++) 
        values[j]+=val; 
    }
    
    for(int j=0; j < values.length; j++) 
      r = ( values[j] > r ? values[j] : r );
    
    return r;
  }
	
	static long arrayManipulation3(int n, int[][] queries) {
    long r = 0;
    long[] values = new long[n];
    
    for( int i=0; i<queries.length; i++ ) {
      int startIndex = queries[i][0] - 1;
      int endIndex = queries[i][1] - 1;
      int val = queries[i][2];
      
      for(int j=startIndex; j<=endIndex; j++) 
        values[j]+=val; 
    }
    
    for(int j=0; j < values.length; j++) 
      r = ( values[j] > r ? values[j] : r );
    
    
    return r;
  }
	
	static long arrayManipulation2(int n, int[][] queries) {
	  long r = 0;
	  long[] values = new long[n];
	  
	  for( int i=0; i<queries.length; i++ ) {
	    int startIndex = queries[i][0] - 1;
	    int endIndex = queries[i][1] - 1;
	    int val = queries[i][2];
	    
	    for(int j=startIndex; j<=endIndex; j++) {
	      long nval = values[j] + val; 
	      values[j] = nval;
	      if( nval > r) r = nval;
	    }
	  }
	  
	  return r;
	}

}
