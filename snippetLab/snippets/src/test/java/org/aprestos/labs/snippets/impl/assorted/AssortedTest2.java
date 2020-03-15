package org.aprestos.labs.snippets.impl.assorted;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AssortedTest2 {


    @Test
    public void test0() {
        int[] A = new int[]{2, 3, 4, 5};

        int K = 4;
        int endBoundry = 0;
        int arraySize = A.length;
        for (int startBoundry = 0; startBoundry < A.length; startBoundry += K) {
            endBoundry = startBoundry + (arraySize - startBoundry > K ? K - 1 : arraySize - startBoundry - 1);
            System.out.println(startBoundry + "=>" + endBoundry);

        }

    }

    @Test
    public void test1() {
        String x = "lsajdlk\nkdjksj";
        String[] h = x.split(System.getProperty("line.separator"));

        System.out.println(h);


    }

    @Test
    public void test2() {
        String x = "lsajdlk\nkdjksj";

        String[] h = x.split(new String(new char[]{10}));

        System.out.println(h);


    }

    int getPhoneNumber(String s) {
        return Integer.parseInt(s.substring(9).replaceAll("-", "").trim());
    }

    int getHours(String s) {
        return Integer.parseInt(s.substring(0, 2).trim());
    }

    int getMinutes(String s) {
        return Integer.parseInt(s.substring(3, 5).trim());
    }

    int getSeconds(String s) {
        return Integer.parseInt(s.substring(6, 8).trim());
    }


    void removeFreeNumber(Map<Integer, Integer> duration, Map<Integer, Integer> costs) {
        Set<Integer> tie = new HashSet<Integer>();
        int min = -1;

        for (Map.Entry<Integer, Integer> d : duration.entrySet()) {
            if (-1 == min) {
                min = d.getValue();
                tie.add(d.getKey());
            } else {
                if (d.getValue() < min) {
                    tie.clear();
                    tie.add(d.getKey());
                    min = d.getValue();
                } else if (d.getValue() == min) {
                    tie.add(d.getKey());
                }
            }
        }
        System.out.println(String.format("removed %d numbers", tie.size()));
        for (Integer number : tie)
            costs.remove(number);

    }

    @Test
    public void test3() {
        int x = 0;

/*        char[] s = Integer.toBinaryString(x).toCharArray();

        for (int p = s.length / 2; p >= 1; p--) {

            for (int i = 0; i + p < s.length; i++) {
                if (s[i] != s[i + p])
                    break;
            }

        }*/
        boolean isit = (null instanceof String);

        System.out.println(solution(365));


    }

    int solution(int n) {
        int[] d = new int[30];
        int l = 0;
        int p;
        while (n > 0) {
            d[l] = n % 2;
            n /= 2;
            l++;
        }
        for (p = 1; p <= l/2; ++p) {
            int i;
            boolean ok = true;
            for (i = 0; i < l - p; ++i) {
                if (d[i] != d[i + p]) {
                    ok = false;
                    break;
                }
            }
            if (ok) {

                return p;
            }
        }
        return -1;
    }

}
