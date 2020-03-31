package org.exercises.jtamv.challenges;

import org.junit.Assert;
import org.junit.Test;

public class FindSmallerIntegerWithSameElementsSum {

    Solution o = new Solution();

    @Test
    public void test() throws Exception {

        Assert.assertEquals(37, o.solution(28));
        Assert.assertEquals(743, o.solution(734));
        Assert.assertEquals(2089, o.solution(1990));
        Assert.assertEquals(10000, o.solution(1000));
    }

    class Solution {

        int max_p = 4;

        public int solution(int N) {
            if( 1 > N || N > 50000 )
                throw new RuntimeException("wrong input");

            int r = 0;
            int[] numbers = getNumbers(N);
            int sum = getSum(numbers);
            int seed = N;
            while( 50000 >= seed ){
                seed++;
                if( sum == getSum(getNumbers(seed))){
                    r = seed;
                    break;
                }

            }
            return r;
        }

        int[] add2Arr(int[] src, int n){
            int al = src.length;
            int[] r = new int[al+1];
            System.arraycopy(src, 0, r,0 , al);
            r[al] = n;
            return r;
        }

        int getSum(int[] s){
            int sum = 0;
            for( int i: s )
                sum += i;
            return sum;
        }

        int[] getNumbers(int N){
            int[] numbers = new int[]{};
            int mod = N;

            for( int i = 4; i >= 0; i-- ){
                int newMod = mod % (int)(Math.pow(10, i));
                if( newMod != N )
                    numbers = add2Arr(numbers, mod/( i != 0 ? (int)(Math.pow(10, i)) : 1));
                mod = newMod;
            }
            return numbers;
        }

    }

}


