package com.tgedr.labs.challenge;

import org.junit.Assert;
import org.junit.Test;

public class FindFairArraySplits {

    Solution o = new Solution();

    @Test
    public void test() throws Exception {
        Assert.assertEquals(2, o.solution(new int[]{4,-1,0,3},new int[]{-2,5,0,3}));
        Assert.assertEquals(1, o.solution(new int[]{2,-2,-3,3},new int[]{0,0,4,-4}));
        Assert.assertEquals(0, o.solution(new int[]{4,-1,0,3},new int[]{-2,6,0,4}));
        Assert.assertEquals(0, o.solution(new int[]{3,2,6},new int[]{4,1,6}));
        Assert.assertEquals(2, o.solution(new int[]{1,4,2,-2,5},new int[]{7,-2,-2,2,5}));
    }

    class Solution {

        public int solution(int[] A, int[] B) {

            int fairs = 0;

            for(int i=1; i < A.length; i++){
                int[] splitSumA = calcSplitSum(A, i);
                if( splitSumA[0] != splitSumA[1] )
                    continue;
                int[] splitSumB = calcSplitSum(B, i);
                if( splitSumB[0] == splitSumB[1] && splitSumB[0] == splitSumA[0])
                    fairs++;
            }


            return fairs;
        }

        int[] calcSplitSum(int [] src, int index){
            int left = 0;
            for( int i=0; i < index; i++ )
                left += src[i];
            int right = 0;
            for( int i = src.length-1; i>=index; i-- )
                right += src[i];

            return new int[]{left,right};
        }

    }



}


