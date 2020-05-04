package com.tgedr.labs.codechallenge.sorting;

public interface Sort {

    int[] sort(int[] a);

    default void exchange(int[] items, int i, int j){
        int swap = items[i];
        items[i] = items[j];
        items[j] = swap;
    }

}
