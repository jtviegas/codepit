package com.tgedr.labs.codechallenge;

import org.junit.Assert;
import org.junit.Test;
import com.tgedr.labs.codechallenge.sorting.*;

public class SortingTest {


    @Test
    public void testInsertionSorter() {

        Sort sorter = new Insertion();

        int[] expected = new int[]{1, 2, 5, 9, 10};
        int[] a = new int[]{2, 10, 9, 1, 5};

        Assert.assertArrayEquals(expected, sorter.sort(a));

    }

    @Test
    public void testSelectionSorter() {

        Sort sorter = new Selection();

        int[] expected = new int[]{1, 2, 5, 9, 10};
        int[] a = new int[]{2, 10, 9, 1, 5};

        Assert.assertArrayEquals(expected, sorter.sort(a));

    }

    @Test
    public void testBubbleSorter() {

        Sort sorter = new Bubble();

        int[] expected = new int[]{1, 2, 5, 9, 10};
        int[] a = new int[]{2, 10, 9, 1, 5};

        Assert.assertArrayEquals(expected, sorter.sort(a));

    }

}
