package org.aprestos.labs.assorted;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Example {

	private String doSomething(String s) {
		char[] chars = s.toCharArray();

		int[] scores = new int[256];
		for (char c : chars)
			scores[c]++;

		int even = 0;
		int odd = 0;

		for (int i : scores) {
			if (i % 2 == 0)
				even++;
			else
				odd++;
		}

		if (1 < odd)
			return "FALSE";
		else
			return "TRUE";

	}

	private int doSomething2(int[] a) {

		int r = -1;
		int min = 1000000, max = -1000000;
		for (int i = 2; i < a.length; i++) {

			if (a[i] > max)
				max = a[i];
			else
				continue;

			for (int j = 1; j < i; j++) {

				if (a[j] <= min)
					min = a[j];
				else
					continue;

				if (a[j] < a[i]) {
					int diff = a[i] - a[j];
					if (diff > r)
						r = diff;
				}
			}
		}
		return r;
	}

	class Comparator {
		boolean compare(int a, int b) {
			return a == b ? true : false;
		}

		boolean compare(String a, String b) {
			return a.equals(b);
		}

		boolean compare(int[] a, int[] b) {
			boolean r = true;
			if (a.length == b.length) {
				for (int i = 0; i < a.length; i++)
					r &= (a[i] == b[i]);
			} else
				r = false;

			return r;
		}
	}

	class Point2D {
		double x, y;

		Point2D(double x, double y) {
			this.x = x;
			this.y = y;
		}

		double dist2D(Point2D p) {
			return Math.sqrt((p.x - x) * (p.x = x) + (p.y - y) * (p.y - y));
		}

		void printDistance(double d) {
			int r = (int) d + (d % 1 > 0 ? 1 : 0);
			System.out.println("2D distance = " + r);
		}

	}

	class Point3D extends Point2D {
		double z;

		Point3D(double x, double y, double z) {
			super(x, y);
			this.z = z;
		}

		double dist3D(Point3D p) {
			return Math.sqrt((p.x - x) * (p.x = x) + (p.y - y) * (p.y - y) + (p.z - z) * (p.z - z));
		}

	}

	private int doSomething3(int x, List<Integer> arr) {

		int c = 0;
		int min = 1000000000;
		int max = -1000000000;

		for (Integer i : arr) {

			if (i < min)
				min = i;

			c++;
			if (c == x) {
				c = 0;
				if (min > max)
					max = min;
				min = 1000000000;
			}
		}

		return max;
	}

	@Test
	public void test() throws Exception {
		// Assert.assertEquals("TRUE", doSomething("aaabbbb"));
		// Assert.assertEquals("FALSE", doSomething("cdefghmnopqrstuvw"));
		// Assert.assertEquals(8, doSomething2(new int[] { 7, 2, 3, 10, 2, 4, 8, 1 }));
		// Assert.assertEquals(2, doSomething2(new int[] { 6, 7, 9, 5, 6, 3, 2 }));
		Assert.assertEquals(3, doSomething3(1, Arrays.asList(1, 2, 3, 1, 2)));
	}

}
