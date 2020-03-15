package org.aprestos.labs.snippets.impl.collections;

import com.google.common.collect.Sets;
import org.apache.commons.collections.ArrayStack;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

/**
 * Unit test for simple App.
 */
public class SplitTest {


	
	@Test
	public void simpleTest1() throws Exception {

		List<Set<String>> r = new ArrayList<>();
		int n=4;
		List<String> o = Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n");

		List<Spliterator<String>> handling = new ArrayList<>();
		List<Spliterator<String>> toHandle = new ArrayList<>();
		List<Spliterator<String>> handled = new ArrayList<>();
		toHandle.add(o.spliterator());

		do {
			handling.addAll(toHandle);
			toHandle.clear();

			for(Spliterator<String> s: handling){
				if( n < s.getExactSizeIfKnown() ){
					toHandle.add(s.trySplit());
					toHandle.add(s);
				}
				else
					handled.add(s);
			}

			handling.clear();
		}
		while( !toHandle.isEmpty() );

		for( Spliterator<String> s: handled ){
			Set<String> ci = new HashSet<>();
			s.forEachRemaining(e -> ci.add(e));
			r.add(ci);
		}

	    Assert.assertEquals(4, r.size());
		Assert.assertEquals(3, r.get(0).size());
		Assert.assertEquals(4, r.get(1).size());
		Assert.assertEquals(3, r.get(2).size());
		Assert.assertEquals(4, r.get(3).size());
	   
	}

	List<Set<String>> partition(int n, List<String> o){
		List<Set<String>> r = new ArrayList<>();

		if( 0 < o.size() ) {
			if (n < o.size()) {
				int i = 0;
				while (i < o.size()) {
					int s = i;
					i = i + n <= o.size() ? i + n : o.size();
					r.add(Sets.newHashSet(o.subList(s, i)));
				}
			} else
				r.add(Sets.newHashSet(o));
		}
		return r;
	}

	@Test
	public void simpleTest2() throws Exception {

		List<Set<String>> r = new ArrayList<>();
		int n=4;
		List<String> o = Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n");

		r = partition(n, o);

		Assert.assertEquals(4, r.size());
		Assert.assertEquals(4, r.get(0).size());
		Assert.assertEquals(4, r.get(1).size());
		Assert.assertEquals(4, r.get(2).size());
		Assert.assertEquals(2, r.get(3).size());

	}

	@Test
	public void simpleTest3() throws Exception {

		List<Set<String>> r = new ArrayList<>();
		int n=14;
		List<String> o = Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n");

		r = partition(n, o);

		Assert.assertEquals(1, r.size());
		Assert.assertEquals(14, r.get(0).size());

	}

	@Test
	public void simpleTest4() throws Exception {

		List<Set<String>> r = new ArrayList<>();
		int n=17;
		List<String> o = Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n");

		r = partition(n, o);

		Assert.assertEquals(1, r.size());
		Assert.assertEquals(14, r.get(0).size());

	}

	@Test
	public void simpleTest5() throws Exception {

		List<Set<String>> r = new ArrayList<>();
		int n=1;
		List<String> o = Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n");

		r = partition(n, o);

		Assert.assertEquals(14, r.size());

	}

	@Test
	public void simpleTest6() throws Exception {

		List<Set<String>> r = new ArrayList<>();
		int n=12;
		List<String> o = Arrays.asList("a","b","c");

		r = partition(n, o);

		Assert.assertEquals(1, r.size());
		Assert.assertEquals(3, r.get(0).size());

	}

	@Test
	public void simpleTest7() throws Exception {

		List<Set<String>> r = new ArrayList<>();
		int n=12;
		List<String> o = Arrays.asList();

		r = partition(n, o);

		Assert.assertEquals(0, r.size());


	}

}
