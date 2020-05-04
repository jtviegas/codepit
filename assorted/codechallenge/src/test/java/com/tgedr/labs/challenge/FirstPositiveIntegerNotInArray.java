package com.tgedr.labs.challenge;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class FirstPositiveIntegerNotInArray {

    private static final int[][] inputs = new int[][]{{ 1, 3, 6, 4, 1, 2}, {1, 2, 3}, {-1, -3} };
    private static final int[] solutions = new int[]{5,4,1};

    @Test
    public void test() throws Exception {
        Solution o = new Solution();
        for(int i=0; i < solutions.length; i++) {
            int[] input = inputs[i];
            int solution = solutions[i];
            Assert.assertEquals(solution, o.solution(input));
        }
    }

    class Solution {

        private static final int MIN_SOLUTION_VALUE = 1;
        private static final int  ARRAY_MAX_LEN = 100000;
        private static final int ARRAY_ELEMENT_MIN = -1000000;
        private static final int  ARRAY_ELEMENT_MAX = 1000000;

        public int solution(int[] a) {

            if( null == a || a.length < 1 || a.length > ARRAY_MAX_LEN )
                throw new RuntimeException("wrong input");

            Arrays.sort(a);
            if( a[0] < ARRAY_ELEMENT_MIN || a[a.length-1] >  ARRAY_ELEMENT_MAX )
                throw new RuntimeException("wrong input");

            int result = 0;
            int index = 0;
            int probable = MIN_SOLUTION_VALUE;

            for( int e: a ){
                if( e < probable )
                    ; // do nothing
                if( e == probable )
                    probable++;
                if( e > probable )
                    break;
            }

            if( a[a.length-1] != probable )
                result = probable;

            return result;

        }

    }

}


