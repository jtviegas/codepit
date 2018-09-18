package org.aprestos.labs.challenges;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RansomNote {

	@Test
	public void test() throws Exception {
		String m = "give me one grand today night";
		String n = "give one grand today";
		Assert.assertEquals("Yes", doit(m.split(" "), n.split(" ")));
	}

	@Test
	public void test1() throws Exception {

		String m = "two times three is not four";
		String n = "two times two is four";

		Assert.assertEquals("No", doit(m.split(" "), n.split(" ")));
	}

	@Test
	public void test2() throws Exception {
		String m = "ive got a lovely bunch of coconuts";
		String n = "ive got some coconuts";
		Assert.assertEquals("No", doit(m.split(" "), n.split(" ")));
	}

	private String doit(String[] magazine, String[] note) {
		String r = "No";

		List<Integer> terms = new ArrayList<Integer>();
		for (String s : note)
			terms.add(s.hashCode());

		for (String s : magazine) {
			Integer val = Integer.valueOf(s.hashCode());
			if (terms.contains(val))
				terms.remove(val);

			if (0 == terms.size()) {
				r = "Yes";
				break;
			}
		}

		return r;
	}

}
