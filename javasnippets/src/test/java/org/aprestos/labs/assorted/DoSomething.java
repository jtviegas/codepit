package org.aprestos.labs.assorted;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

public class DoSomething {

	@Test
	public void test_00() {

		int[][] u = new int[][] { { 4, 8, 2 }, { 4, 5, 7 }, { 6, 1, 6 } };
		Assert.assertEquals(3, magicSquare(u));

	}

	public Integer sol(String s) {

		Integer r = null;

		final Map<Integer, Integer> sink = new LinkedHashMap<Integer, Integer>();
		IntStream intStream1 = s.codePoints();

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

}
