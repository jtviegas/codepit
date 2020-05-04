package com.tgedr.labs.challenge.assorted.codty.glrelay;

import com.tgedr.labs.exercises.Exercise;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SolutionTest {

    private Solution o;

    @Before
    public void setUp() throws Exception {
        o = new Solution();
    }

    @Test
    public void test01() {

        int[] input = {3, 4, 5, 3, 7};
        int expected = 3;
        assertEquals(expected, o.solution(input));
    }

    @Test
    public void test02() {

        int[] input = {1, 2, 3, 4};
        int expected = -1;
        assertEquals(expected, o.solution(input));
    }

    @Test
    public void test03() {

        int[] input = {1, 3, 1, 2};
        int expected = 0;
        assertEquals(expected, o.solution(input));
    }

    @Test
    public void test04() {
        int[] input = {1, 3, 1, 2};
        assertEquals(true, o.isPleasing(input));
        int[] input2 = {3, 3, 1, 2};
        assertEquals(false, o.isPleasing(input2));
        int[] input3 = {0, 1, 0, 1};
        assertEquals(true, o.isPleasing(input3));
    }

    @Test
    public void test05() {

        int[] input = {1, 2, 2, 4,6,4,9};
        int expected = -1;
        assertEquals(expected, o.solution(input));
    }

}
