package com.tgedr.labs.codechallenge.sorting;

public class Insertion implements Sort {

    @Override
    public int[] sort(int[] a) {

        for(int i=1; i < a.length; i++){
            for(int j = i; j > 0; j--){
                if( a[j] < a[j-1] )
                    exchange(a, j, j-1);
            }
        }

        return a;
    }



}
