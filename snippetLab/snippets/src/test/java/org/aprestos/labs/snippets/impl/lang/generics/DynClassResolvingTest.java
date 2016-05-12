package org.aprestos.labs.snippets.impl.lang.generics;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DynClassResolvingTest {


	
	@Test
	public void firstSerializationTest() throws FileNotFoundException, ClassNotFoundException {
		
		DynamicClassResolving o = new DynamicClassResolving();
		
		List<Letter> basket = new ArrayList<Letter>();
		basket.add(new A("a1"));
		basket.add(new A("a2"));
		basket.add(new A("a3"));
		basket.add(new B("b1"));
		basket.add(new B("b2"));
		
		@SuppressWarnings("unused")
		List<Letter> as = o.sortByType(basket, A.class);
		@SuppressWarnings("unused")
		List<Letter> bs = o.sortByType(basket, B.class);
		
		
	}
	




}
