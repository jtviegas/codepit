package com.tgedr.labs.codechallenge.sorting;

public class Selection implements Sort {

    @Override
    public int[] sort(int[] a) {

        for(int i=0; i < a.length; i++){
            int min = i;
            // find a lessser one
            for(int j = i+1; j < a.length; j++)
                if( a[j] < a[min] )
                    min=j;
            if(min != i)
                exchange(a, min, i);
        }

        return a;
    }



}
