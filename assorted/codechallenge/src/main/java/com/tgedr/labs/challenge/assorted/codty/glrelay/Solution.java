package com.tgedr.labs.challenge.assorted.codty.glrelay;

public class Solution {


    public int solution(int[] A){

        if( isPleasing(A) )
            return 0;

        int result = 0;
        for( int i=0; i < A.length; i++ ){
            int[] suggestedArray = removeFromArr(A, i);
            if( isPleasing(suggestedArray)  )
                result++;
        }

        return result == 0 ? -1 : result;
    }

    boolean isPleasing(int[] A){
        boolean r = true;
        for( int i=1;i<A.length-1;i++ ){
            r &= (A[i-1] > A[i] && A[i] < A[i+1])
                    ||
                    (A[i-1] < A[i] && A[i] > A[i+1]) ;
        }
        return r;
    }

    int[] removeFromArr(int[] src, int n){
        int[] r = new int[src.length-1];
        int newIndex = 0;
        for(int i=0; i < src.length ;i++){
            if(i != n)
                r[newIndex++] = src[i];
        }
        return r;
    }





}
