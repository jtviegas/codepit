package org.exercises.jtamv.commons;

import org.junit.Assert;
import org.junit.Test;

public class Challenge {

    Solution o = new Solution();

    @Test
    public void test() throws Exception {
        Assert.assertEquals(0, o.solution(new int[] {}));
    }

    class Solution {

        public int solution(int[] a) {
            return 0;
        }

    }

}


