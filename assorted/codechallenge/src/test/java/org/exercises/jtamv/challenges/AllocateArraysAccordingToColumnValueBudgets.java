package org.exercises.jtamv.challenges;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class AllocateArraysAccordingToColumnValueBudgets {

    Solution o = new Solution();

    @Test
    public void test() throws Exception {
        Assert.assertEquals("11100,10001", o.solution(3,2, new int[] {2,1,1,0,1}));
    }

    class Solution {

        String NO_SOLUTION="IMPOSSIBLE";

        public String solution(int U, int L, int[] C) {

            int[][] r = new int[2][C.length];
            Arrays.fill(r[0], 0);
            Arrays.fill(r[1], 0);

            for(int i=0; i<C.length;i++){

                int target = C[i];
                int upperCost = sumRow(r,0);
                int lowerCost = sumRow(r,1);
                int upperBudget = U - upperCost;
                int lowerBudget = L - lowerCost;

                if( target >  upperBudget + lowerBudget )
                    return NO_SOLUTION;

                if(2 == target && 1 <= upperBudget && 1 <= lowerBudget && 1+upperCost <= U && 1+lowerCost <= L){
                    r[0][i]++;
                    r[1][i]++;
                }
                else if(1 == target && 1 <= upperBudget && 1+upperCost <= U){
                    r[0][i]++;
                }
                else if(1 == target && 1 <= lowerBudget && 1+lowerCost <= L){
                    r[1][i]++;
                }
                else if( 0 != target){
                    return NO_SOLUTION;
                }

            }

            return asString(r);
        }

        int sumRow(int[][] src, int i){
            int sum = 0;
            for( int e: src[i])
                sum+=e;
            return sum;
        }

        String asString(int[][] src){
            StringBuffer r = new StringBuffer();
            int rl = src.length;
            int cl = src[0].length;
            for(int i=0; i<rl; i++){
                if( i != 0)
                    r.append(",");
                for(int j=0; j<cl; j++){
                    r.append(src[i][j]);
                }
            }
            return r.toString();
        }

    }

}


