package org.aprestos.labs.assorted;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.Test;

public class DoSomething {

	@Test
	public void test_00() {

		int[] u = new int[] { 80, 80, 1000000000, 80, 80, 80, 80, 80, 80, 123456789 };
		solution(u);

	}

	public int solution(int[] T) {
		Set<Integer> map = new HashSet<Integer>();
		int[] mine = new int[T.length / 2];
		int[] his = new int[T.length / 2];
		int mineIndex = 0;
		int hisIndex = 0;

		for (int i : T) {
			if (!map.contains(i) && mineIndex < T.length / 2) {
				mine[mineIndex++] = i;
				map.add(i);
			} else
				his[hisIndex++] = i;

		}

		return mine.length;
	}

	public char sol(String s) {

		Integer r = null;

		final Map<Integer, Integer> sink = new HashMap<Integer, Integer>();
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

	}

}
