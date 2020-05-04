package com.tgedr.labs.challenge;

import org.junit.Assert;
import org.junit.Test;

/**
 * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai).
 * n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two lines,
 * which together with x-axis forms a container, such that the container contains the most water.
 */
public class ContainerWithMostWater {

    Solution o = new Solution();

    class Solution {

        public int solution(int[] a) {

            int water = 0;

            for( int i=0; i< a.length-1; i++){

                for(int j=i+1; j < a.length; j++){
                    int w = (j-i) * Math.min(a[i],a[j]);
                    if( w >  water)
                        water = w;
                }
            }

            return water;
        }


    }



    @Test
    public void test() throws Exception {
        Assert.assertEquals(49, o.solution(new int[] {1,8,6,2,5,4,8,3,7}));
        Assert.assertEquals(4, o.solution(new int[] {1,2,4,3}));
    }



}


