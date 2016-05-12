package org.aprestos.labs.snippets.impl.algo.assorted.flattener;

import org.aprestos.labs.snippets.impl.algo.assorted.flattener.SortOfGenericArrayFlattener;
import org.junit.Assert;
import org.junit.Test;

public class SortOfGenericArrayFlattenerTest {

	@Test
	public void testFlatteningIntegersA() throws NegativeArraySizeException, IllegalAccessException, InstantiationException {

		Object[] source = new Object[]{
				1,4, 
				 new Integer[]{3,4},
				 new Integer[][]{
						new Integer[]{3,4,5}  , 
						new Integer[]{8987,765}
						},
		};
		Integer[] expected = new Integer[]{ 1,4,3,4,3,4,5,8987,765 };
		
		SortOfGenericArrayFlattener<Integer> flattener = new SortOfGenericArrayFlattener<Integer>(new Integer(0).getClass());
		Integer[] actual = flattener.flatten(source);
		Assert.assertArrayEquals(expected, actual);
		
	}
	
	@Test
	public void testFlatteningIntegersB() throws NegativeArraySizeException, IllegalAccessException, InstantiationException {

		Object[] source = new Object[]{
				new Object[] {
						1,
						2,
						new Integer[]{3}
				},
				4
		};
		Integer[] expected = new Integer[]{ 1,2,3,4 };
		
		SortOfGenericArrayFlattener<Integer> flattener = new SortOfGenericArrayFlattener<Integer>(new Integer(0).getClass());
		Integer[] actual = flattener.flatten(source);
		Assert.assertArrayEquals(expected, actual);
		
	}

	
	@Test
	public void testFlatteningIntegersC() throws NegativeArraySizeException, IllegalAccessException, InstantiationException {

		Object[] source = new Object[]{
				new Object[] {
						1,
						2,
						new Short[]{3}
				},
				4
		};
		Integer[] expected = new Integer[]{ 1,2,4 };
		
		SortOfGenericArrayFlattener<Integer> flattener = new SortOfGenericArrayFlattener<Integer>(new Integer(0).getClass());
		Integer[] actual = flattener.flatten(source);
		Assert.assertArrayEquals(expected, actual);
		
	}
	
	@Test
	public void testFlatteningIntegersD() throws NegativeArraySizeException, IllegalAccessException, InstantiationException {

		Object[] source = new Object[]{
				new Object[] {
						1,
						2,
						new Object[]{3}
				},
				4
		};
		Integer[] expected = new Integer[]{ 1,2,3,4 };
		
		SortOfGenericArrayFlattener<Integer> flattener = new SortOfGenericArrayFlattener<Integer>(new Integer(0).getClass());
		Integer[] actual = flattener.flatten(source);
		Assert.assertArrayEquals(expected, actual);
		
	}
	
	@Test
	public void testFlatteningLongs() throws NegativeArraySizeException, IllegalAccessException, InstantiationException {

		Object[] source = new Object[]{
				1l,4l, 
				 new Long[]{3l,4l},
				 new Object[][]{
						new Long[]{3l,4l,5l}  , 
						new Long[]{8987l,765l}
						 , 
							new Object[]{
								new Long[]{8l,9l,10l}  , 
								new Long[]{11l,12l},
								new Object[]{
										new Long[]{13l,14l,15l}  , 
										new Long[]{16l,17l}
										}
								}
						},
						33l,
						909l
		};
		Long[] expected = new Long[]{ 1l,4l,3l,4l,3l,4l,5l,8987l,765l,8l,9l,10l,11l,12l,13l,14l,15l,16l,17l,33l,909l };
		
		SortOfGenericArrayFlattener<Long> flattener = new SortOfGenericArrayFlattener<Long>(new Long(0).getClass());
		Long[] actual = flattener.flatten(source);
		Assert.assertArrayEquals(expected, actual);
		
	}

}
