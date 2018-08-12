package org.aprestos.labs.assorted;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

public final class DoSomething {

	@Test
	public void test_00() {

		int[][] u = new int[][] { { 4, 8, 2 }, { 4, 5, 7 }, { 6, 1, 6 } };
		int[][] u2 = new int[][] { { 4, 8, 2, 1, 3 }, { 4, 5, 7, 8, 4 }, { 6, 1, 6, 5, 1 }, { 4, 5, 7, 1, 2 },
				{ 6, 1, 6, 4, 9 } };

		Assert.assertArrayEquals(rotate(u), new int[][] { { 2, 7, 6 }, { 8, 5, 1 }, { 4, 4, 6 } });
		Assert.assertArrayEquals(rotate(u2), new int[][] { { 3, 4, 1, 2, 9 }, { 1, 8, 5, 1, 4 }, { 2, 7, 6, 7, 6 },
				{ 8, 5, 1, 5, 1 }, { 4, 4, 6, 4, 6 } });

		Assert.assertEquals(findEdgeCentralOne(new int[][] { { 2, 1, 6 }, { 8, 5, 1 }, { 4, 4, 6 } }), 0);
		Assert.assertEquals(findEdgeCentralOne(new int[][] { { 2, 3, 6 }, { 8, 5, 1 }, { 4, 4, 6 } }), 3);
		Assert.assertEquals(findEdgeCentralOne(u2), 3);

		Assert.assertArrayEquals(transform2MagicSquare(new int[][] { { 4, 8, 2 }, { 4, 5, 7 }, { 6, 6, 1 } }),
				new int[][] { { 4, 1, 2 }, { 4, 5, 7 }, { 6, 6, 8 } });

		Assert.assertArrayEquals(transform2MagicSquare(new int[][] { { 4, 8, 2 }, { 4, 5, 1 }, { 6, 6, 2 } }),
				new int[][] { { 2, 1, 2 }, { 8, 5, 6 }, { 4, 4, 6 } });

	}

	private int[][] transform2MagicSquare(int[][] m) {

		int orientation = findEdgeCentralOne(m);
		if (0 > orientation) // there is no startPoint
			m = findValueAndSwapIt(1, m.length / 2, m);
		else if (0 < orientation)
			m = rotateNtimes(4 - orientation, m);

		return m;

	}

	private int[][] findValueAndSwapIt(int val, int pos, int[][] m) {
		boolean found = false;
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				if (val == m[i][j]) {
					found = true;
					int x = pos / m.length;
					int y = pos % m.length;
					int val0 = m[x][y];
					m[x][y] = val;
					m[i][j] = val0;
					break;
				}
			}
		}
		if (!found)
			throw new IllegalArgumentException("couldn't find the value to swap position");

		return m;
	}

	private int findEdgeCentralOne(int[][] o) {

		int r = -1;
		int n = o.length;

		int endIndex = n - 1;
		if (1 == o[0][n / 2]) {
			r = 0; // north
		} else if (1 == o[n / 2][0]) {
			r = 1; // west
		} else if (1 == o[endIndex][n / 2]) {
			r = 2; // south
		} else if (1 == o[n / 2][endIndex]) {
			r = 3; // east
		}

		return r;
	}

	private int[][] copy(int[][] o) {
		int[][] r = new int[o.length][o[0].length];

		for (int i = 0; i < o.length; i++) {
			for (int j = 0; j < o.length; j++) {
				r[i][j] = o[i][j];
			}
		}

		return r;
	}

	private int[][] rotateNtimes(int n, int[][] m) {
		for (int i = 0; i < n; i++) {
			m = rotate(m);
		}
		return m;
	}

	private int[][] rotate(int[][] m) {
		int n = m[0].length;
		int[][] d = copy(m);
		for (int i = 0; i < n / 2; i++) {
			rotateLayer(i, m, d);
		}
		return d;
	}

	private int[][] rotateLayer(int layer, int[][] source, int[][] destin) {

		int len = source.length;

		int startIndex = layer;
		int endIndex = len - startIndex - 1;

		for (int i = startIndex, j = endIndex; i <= endIndex; i++, j--) {
			destin[j][startIndex] = source[startIndex][i]; // N -> W
			destin[endIndex][j] = source[j][startIndex]; // W -> S
			destin[i][endIndex] = source[endIndex][j]; // S -> E
			destin[startIndex][i] = source[i][endIndex]; // E -> N
		}

		return destin;
	}

	// #########################################################################

	public Integer sol(String s) {

		Integer r = null;

		final Map<Integer, Integer> sink = new LinkedHashMap<Integer, Integer>();
		Integer fr = sink.get(1), ft = sink.get(2);

		IntStream intStream1 = s.codePoints();
		short rd = 6;
		Short r3 = new Short(rd);
		int u = r3;

		intStream1.forEach(c -> {
			Integer k = sink.get(c);
			if (null == k)
				sink.put(new Integer(c), new Integer(1));
			else
				sink.put(new Integer(c), k++);
		});

		Integer comparison = new Integer(1);
		for (Map.Entry<Integer, Integer> entry : sink.entrySet()) {
			if (comparison.equals(entry.getValue())) {
				r = entry.getKey();
				break;
			}

		}

		return r;
	}

	private int sum(int[] s) {
		int r = 0;
		for (int i = 0; i < s.length; i++)
			r += s[i];
		return r;
	}

	private int[] findMissing(int[][] s) {
		int[] r = new int[s[0].length];

		return r;
	}

	private int[] findDuplicates(int[][] s) {
		int[] r = new int[s[0].length];

		return r;
	}

	public int magicSquare(int[][] s) {
		int r = 0;

		int n = s[0].length;

		int[] sample = new int[n];
		for (int i = 1, j = 0; i <= n; i++, j++)
			sample[j] = i;

		int lineValue = (n * (n ^ 2 + 1)) / 2;

		return r;
	}

	/*
	 * private int[] getNorth() { }
	 * 
	 * private int[] getSouth() { }
	 * 
	 * private int[] getEast() { }
	 * 
	 * private int[] getWest() { }
	 */

	private int nextPosition(int n, int position, int val, int[][] m) {
		int r = -1;

		int row = position / 3;
		int column = position % n;

		int next_row = row - 1;
		int next_column = column + 1;

		boolean changing = true;
		while (changing) {
			if (next_row < 0 && next_column == n) {
				next_row = n - 1;
				next_column = column;
			} else if (next_column == n) {
				next_column = 0;
			} else if (next_row < 0) {
				next_row = n - 1;
			} else if (0 != (m[next_row][next_column]) && val > m[next_row][next_column]) {
				next_row = row + 1;
				next_column = column;
			} else
				changing = false;
		}
		r = n * next_row + next_column;
		return r;
	}

	public int[][] createMagicSquare(int n) {
		if (n % 2 == 0)
			throw new RuntimeException("we can only create odd squares");

		int[][] r = new int[n][n];

		int value = 0;
		int column = n / 2;
		int row = 0;
		r[row][column] = ++value;
		int position = n * row + column;

		do {
			position = nextPosition(n, position, value, r);
			r[position / 3][position % n] = ++value;
		} while ((n * n) > value);

		return r;
	}

	private int findFirstElement(int[][] m) {

		int n = m[0].length;
		int northpos = -1, pos = -1;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int val = m[i][j];
				if (1 == val) {

				}
			}
		}
		// FIXME
		if (n == 0 && n / 2 == 0)
			return 1;
		else if (1 == m[n / 2][0])
			return 2;
		else if (1 == m[n - 1][n / 2])
			return 3;
		else if (1 == m[n / 2][n - 1])
			return 4;

		return 0;
	}

}
