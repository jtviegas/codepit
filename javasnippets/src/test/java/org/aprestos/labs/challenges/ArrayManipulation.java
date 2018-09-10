package org.aprestos.labs.challenges;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArrayManipulation {

	@Test
	public void test() throws Exception {

		Assert.assertEquals(200l, arrayManipulation(5, Utils
				.LinesToIntMatrix("1 2 100£2 5 100£3 4 100".replaceAll("£", System.getProperty("line.separator")))));

		Assert.assertEquals(10l, arrayManipulation(10,
				Utils.LinesToIntMatrix("1 5 3£4 8 7£6 9 1".replaceAll("£", System.getProperty("line.separator")))));

		Assert.assertEquals(31l, arrayManipulation(10, Utils
				.LinesToIntMatrix("2 6 8£3 5 7£1 8 1£5 9 15".replaceAll("£", System.getProperty("line.separator")))));

	}

	static long arrayManipulation(int n, int[][] queries) {

		long[] start = new long[n];
		long[] end = new long[n];

		for (int i = 0; i < queries.length; i++) {
			int startIndex = queries[i][0] - 1;
			int endIndex = (queries[i][1]) < n ? (queries[i][1]) : -1;
			long value = queries[i][2];
			start[startIndex] += value;
			if (endIndex > -1)
				end[endIndex] -= value;
		}

		long toAdd = 0, max = 0;
		for (int i = 0; i < n; i++) {
			toAdd += start[i] + end[i];
			max = toAdd > max ? toAdd : max;
		}

		return max;
	}

	static long arrayManipulation3(int n, int[][] queries) {
		long r = 0;
		long[] values = new long[n];

		for (int i = 0; i < queries.length; i++) {
			int startIndex = queries[i][0] - 1;
			int endIndex = queries[i][1] - 1;
			int val = queries[i][2];

			for (int j = startIndex; j <= endIndex; j++)
				values[j] += val;
		}

		for (int j = 0; j < values.length; j++)
			r = (values[j] > r ? values[j] : r);

		return r;
	}

	static long arrayManipulation2(int n, int[][] queries) {
		long r = 0;
		long[] values = new long[n];

		for (int i = 0; i < queries.length; i++) {
			int startIndex = queries[i][0] - 1;
			int endIndex = queries[i][1] - 1;
			int val = queries[i][2];

			for (int j = startIndex; j <= endIndex; j++) {
				long nval = values[j] + val;
				values[j] = nval;
				if (nval > r)
					r = nval;
			}
		}

		return r;
	}

}
