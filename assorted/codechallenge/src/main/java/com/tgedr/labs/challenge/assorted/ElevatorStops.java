package com.tgedr.labs.challenge.assorted;

import java.util.Set;
import java.util.TreeSet;

public class ElevatorStops {

    public static int getNumOfStops(int[] A, int[] B, int M, int X, int Y)  {

	Set<Integer> targetFloors = new TreeSet<Integer>();
	int stops = 0;
	int weight = 0;
	int load = 0;
	
	int i = 0;
	while(i < A.length){
	    
	    if( (weight + A[i]) <= Y && load < X){
		load++;
		weight += A[i];
		if(targetFloors.add(B[i]))
		    stops++;
		i++;
	    }
	    else { //fullwe'lll have to stop again to reload
		targetFloors.clear();
		load = 0;
		weight = 0;
		stops++;
	    }
	    

	}
	stops++;
	
	return stops;
    }
}
