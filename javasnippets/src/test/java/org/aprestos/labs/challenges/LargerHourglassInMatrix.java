package org.aprestos.labs.challenges;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LargerHourglassInMatrix {

	private int getHourGlassSum(int r, int c, int[][] m) {
		int s = 0;

		for (int i = c; i < c + 3; i++) {
			s += m[r][i];
			s += m[r + 2][i];
		}
		s += m[r + 1][c + 1];

		return s;
	}

	private int doit(int[][] m) {
		int maxSum = Integer.MIN_VALUE;

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				int sum = getHourGlassSum(row, col, m);
				if (maxSum < sum)
					maxSum = sum;
			}
		}

		return maxSum;
	}

	@Test
	public void test() throws Exception {

		Assert.assertEquals(19, doit(new int[][] { { 1, 1, 1, 0, 0, 0 }, { 0, 1, 0, 0, 0, 0 }, { 1, 1, 1, 0, 0, 0 },
				{ 0, 0, 2, 4, 4, 0 }, { 0, 0, 0, 2, 0, 0 }, { 0, 0, 1, 2, 4, 0 } }));

		Assert.assertEquals(-6,
				doit(new int[][] { { -1, -1, 0, -9, -2, -2 }, { -2, -1, -6, -8, -2, -5 }, { -1, -1, -1, -2, -3, -4 },
						{ -1, -9, -2, -4, -4, -5 }, { -7, -3, -3, -2, -9, -9 }, { -1, -3, -1, -2, -4, -5 } }));

	}

}
