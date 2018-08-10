package org.aprestos.labs.assorted;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.Test;

public final class DoSomething {

	class D {
		public int x = 6;

		D() {
			DoSomething.this.sol("s");
		}
	}

	@Test
	public void test_00() {

		// int[][] u = new int[][] { { 4, 8, 2 }, { 4, 5, 7 }, { 6, 1, 6 } };
		// Assert.assertEquals(3, magicSquare(u));
		/*
		 * String ar[] = { "", "e" }; ar[1] = "k"; D f = new D();
		 * System.out.println(f.x);
		 * 
		 * PriorityQueue<String> o = new PriorityQueue<>(Comparator.naturalOrder());
		 * o.add("a"); o.add("b"); o.offer("c"); o.offer("c");
		 * System.out.println(o.peek()); System.out.println(o.poll());
		 * 
		 * Map<String, Integer> g = new HashMap<>();
		 * 
		 * int t = 4, v = 0; ; try { double b = t / v; } catch (Exception e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		int x = 3 & 5;
		int y = 3 | 5;
		System.out.println(x);

	}

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

}
