package org.aprestos.labs.challenges;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MinStepsToSort {

	@Test
	public void test() throws Exception {

		/*
		 * Assert.assertArrayEquals( new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
		 * 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31,
		 * 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50 },
		 * minSwapsToSortSortRationale(strToIntArray(
		 * "2 31 1 38 29 5 44 6 12 18 39 9 48 49 13 11 7 27 14 33 50 21 46 23 15 26 8 47 40 3 32 22 34 42 16 41 24 10 4 28 36 30 37 35 20 17 45 43 25 19"
		 * )));
		 */

		Assert.assertEquals(46, minStepsToSort(strToIntArray(
				"2 31 1 38 29 5 44 6 12 18 39 9 48 49 13 11 7 27 14 33 50 21 46 23 15 26 8 47 40 3 32 22 34 42 16 41 24 10 4 28 36 30 37 35 20 17 45 43 25 19")));

	}

	private int minStepsToSort(int[] arr) {

		int steps = 0;
		Map<Integer, Integer> r = sortAndMap2(arr);

		int index = 0;
		for (Map.Entry<Integer, Integer> entry : r.entrySet()) {

			if (arr[index] != entry.getKey().intValue()) {

				swap(arr, entry.getValue(), index);
				int swappedValue = arr[entry.getValue()];
				r.put(new Integer(swappedValue), entry.getValue());
				steps++;
			}

			index++;
		}
		return steps;
	}

	private Map<Integer, Integer> sortAndMap2(int[] arr) {

		int[] a = new int[arr.length];
		System.arraycopy(arr, 0, a, 0, arr.length);

		Map<Integer, Integer> initialMap = new HashMap<>();
		for (int i = 0; i < a.length; i++)
			initialMap.put(a[i], i);

		Arrays.sort(a);
		Map<Integer, Integer> finalMap = new LinkedHashMap<>();
		for (int i = 0; i < a.length; i++)
			finalMap.put(a[i], initialMap.get(a[i]));

		return finalMap;

	}

	private Map<Integer, Integer> sortAndMap(int[] a) {

		int[][] r = new int[a.length][];

		for (int i = 0; i < a.length; i++) {
			r[i] = new int[] { a[i], i };
		}

		Map<Integer, Integer> m = new LinkedHashMap<>();
		sortAndKeepScore(r, 0, a.length - 1);
		for (int[] i : r)
			m.put(i[0], i[1]);

		return m;

	}

	private void sortAndKeepScore(int[][] a, int lo, int hi) {

		if (lo >= hi)
			return;

		int v = a[lo][0];
		int l = lo, i = lo;
		int h = hi;

		while (h >= i) {

			if (a[i][0] < v) {
				swap2(a, i, l);
				i++;
				l++;
			} else if (a[i][0] > v) {
				swap2(a, i, h);
				h--;
			} else
				i++;
		}

		sortAndKeepScore(a, lo, l - 1);
		sortAndKeepScore(a, h + 1, hi);

	}

	private void swap2(int[][] a, int i, int j) {
		int[] v = a[i];
		a[i] = a[j];
		a[j] = v;
	}

	private int[] swap(int[] a, int i, int j) {
		int v = a[i];
		a[i] = a[j];
		a[j] = v;
		return a;
	}

	private int[] strToIntArray(String s) {

		String[] _s = s.split(" ");
		int[] r = new int[_s.length];
		int index = 0;
		for (String o : _s)
			r[index++] = Integer.parseInt(o.trim());

		return r;
	}

}
