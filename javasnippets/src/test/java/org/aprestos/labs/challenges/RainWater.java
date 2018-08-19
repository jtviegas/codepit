package org.aprestos.labs.challenges;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/*
	Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.
	Example: Input: [0,1,0,2,1,0,1,3,2,1,2,1]
	Output: 6
	The elevation map is represented in the example is represented by the black lines in the image below. The blue lines represent the water that will be trapped.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RainWater {

	int maxDepth = 6;

	private int[][] cleanUpDryRecords(int[][] a) {
		int[][] r = new int[][] {};
		for (int i = 0; i < a.length; i++) {
			boolean empty = true;
			for (int j = 0; j < a[i].length; j++) {
				if (0 < a[i][j]) {
					empty = false;
					break;
				}
			}
			if (!empty) {
				int newSize = r.length + 1;
				int[][] n = new int[newSize][];
				System.arraycopy(r, 0, n, 0, r.length);
				n[r.length] = a[i];
				r = n;
			}
		}

		return r;
	}

	private int[][] addRecord(int[][] a, int[] n) {
		int[][] r = new int[a.length + 1][];
		System.arraycopy(a, 0, r, 0, a.length);
		r[a.length] = n;
		return r;
	}

	private int[][] dip(int[][] a, int level, int o) {
		int[] n = new int[maxDepth];
		for (int i = 0; i < Math.abs(o); i++) {
			n[level + i] = 1;
		}
		return addRecord(a, n);
	}

	private int surface(int[][] a, int level) {
		int r = 0;
		for (int i = 0; i < a.length; i++) {

			for (int j = 0; j < a[i].length; j++) {

				if (j < level && 0 < a[i][j]) {
					r++;
					a[i][j] = 0;
				}

			}

		}
		return r;
	}

	private int solve(int[] s) {

		int[][] record = new int[][] {};
		int waterHeld = 0;
		int previous = 0, under = 0, change = 0;

		for (int i = 0; i < s.length; i++) {

			int level = s[i];
			change = level - previous;
			under += change;
			if (0 <= change) {
				waterHeld += surface(record, level);
				record = cleanUpDryRecords(record);
			}
			if (0 > under)
				record = dip(record, level, under);
			else
				under = 0;

			previous = level;
		}

		return waterHeld;
	}

	static int maxdepth = 6;

	private int solve2(int[] s) {
		int outcome = 0;

		int previous = 0, under = 0;
		int[] water = new int[maxdepth];

		for (int i = 0; i < s.length; i++) {
			int level = s[i];
			int change = level - previous;
			under += change;
			if (0 > under) {
				for (int j = level; j < level + Math.abs(under); j++) {
					water[j]++;
				}
			}
			if (0 < change) {
				for (int j = 0; j < level; j++) {
					outcome += water[j];
					water[j] = 0;
				}
			}
			if (under > 0)
				under = 0;
			previous = level;

		}

		return outcome;
	}

	@Test
	public void test() throws Exception {

		Assert.assertEquals(0, solve2(new int[] { 0, 0 }));
		Assert.assertEquals(6, solve2(new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 }));
		Assert.assertEquals(8, solve2(new int[] { 1, 0, 3, 0, 0, 3, 1, 0, 1 }));
		Assert.assertEquals(0, solve2(new int[] { 1, 0 }));
		Assert.assertEquals(16, solve2(new int[] { 3, 2, 4, 1, 0, 0, 0, 2, 1, 3, 1, 1, 0, 1 }));

	}

}
