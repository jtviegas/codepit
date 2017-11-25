package org.exercises.jtamv.commons;

public class NumericOps {

    /**
     * given an array, calculate the index from which
     * to the left and to the right the related subarrays
     * sum the same total
     * @param A
     * @return
     */
    public static int balanceArray(int[] A) {
	int result = -1;

	long totalLeft = 0;
	long totalRight = getArrayTotal(A);

	for (int i = 0; i < A.length; i++) {
	    totalRight -= A[i];
	    if (i > 0)
		totalLeft += A[i - 1];

	    if (totalRight == totalLeft) {
		result = i;
		break;
	    }

	}

	return result;
    }

    public static long getArrayTotal(int[] A) {
	long result = 0;
	for (int i = 0; i < A.length; i++)
	    result += A[i];
	return result;
    }
    
    public static int getMinimum(int[] A) {

	int result = A[0];

	for (int i = 1; i < A.length; i++) {
	    if (A[i] < result)
		result = A[i];
	}

	return result;
    }
    
    public static int biggestSequenceOfAscendingValues(int[] A) {

   	int result = -1;
   	int maxLength = 0;

   	//loop through all the items
   	for (int i = 0; i < A.length; i++) {

   	    int length = 1;
   	    int j1 = i, j2 = i + 1;
   	    while (j2 < A.length && A[j1] < A[j2]) {
   		length++;
   		j1++;
   		j2++;
   	    }
   	    if (length > maxLength) {
   		maxLength = length;
   		result = i;
   	    }
   	}

   	return result;
       }
    
    //get minimum operations to reach a number
    //operations are double th current number or add 1
    public static int minimumOperationsToNumber(int N) {
	int result = 1;
	int start = N;
	while( (start/=2) >= 1 ){
	    if(start != 1 && start%2 != 0){ //odd and not 1
		start--;
		result++;
	    }
	    result++;
	}
	return result;
    }
    
    
    public static boolean isOneSwapEnough2beSortedAscending(int[] A) {

	int swaps = 0;
	
	int j = A.length-1;
	
	while(j > 0){
	    int i = 0;
	    while(i < j){
		if(A[i] > A[j]){
		    swap(A, i, j);
		    swaps++;
		}
		i++;
	    }
	    
	    j--;
	}
	
	return (swaps <= 1);
    }
    
    public static void swap(int[] a, int i, int j){
	int o = a[i];
	a[i]=a[j];
	a[j]=o;
    }

}
