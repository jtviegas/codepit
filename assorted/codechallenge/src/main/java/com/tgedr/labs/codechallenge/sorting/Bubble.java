package com.tgedr.labs.codechallenge.sorting;

public class Bubble implements Sort {

    @Override
    public int[] sort(int[] a) {

        for(int i=0; i < a.length; i++){
            int exchanges = 0;
            for(int j = a.length-1; j > i; j--){
                if( a[j] < a[j-1] ) {
                    exchange(a, j, j - 1);
                    exchanges++;
                }
            }
            if(0==exchanges) break;
        }

        return a;
    }



}
