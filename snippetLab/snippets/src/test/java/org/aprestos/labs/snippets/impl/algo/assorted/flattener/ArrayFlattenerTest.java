package org.aprestos.labs.snippets.impl.algo.assorted.flattener;

import org.aprestos.labs.snippets.impl.algo.assorted.flattener.ArrayFlattener;
import org.junit.Assert;
import org.junit.Test;

public class ArrayFlattenerTest {

	@Test
	public void testFlatteningIntegersA() throws Exception {

		Object[] source = new Object[]{
				1,4, 
				 new int[]{3,4},
				 new int[][]{
						new int[]{3,4,5}  , 
						new int[]{8987,765}
						},
		};
		int[] expected = new int[]{ 1,4,3,4,3,4,5,8987,765 };
		
		ArrayFlattener flattener = new ArrayFlattener();
		int[] actual = flattener.flatten(source);
		Assert.assertArrayEquals(expected, actual);
		
	}
	
	@Test
	public void testFlatteningIntegersB() throws Exception {

		Object[] source = new Object[]{
				new Object[] {
						1,
						2,
						new Integer[]{3}
				},
				4
		};
		int[] expected = new int[]{ 1,2,3,4 };
		
		ArrayFlattener flattener = new ArrayFlattener();
		int[] actual = flattener.flatten(source);
		Assert.assertArrayEquals(expected, actual);
		
	}


	@Test(expected = IllegalArgumentException.class)
	public void testFlatteningIntegersWithAShort() throws Exception {

		Object[] source = new Object[]{
				new Object[] {
						1,
						2,
						new Short[]{3}
				},
				4
		};
		int[] expected = new int[]{ 1,2,4 };
		
		ArrayFlattener flattener = new ArrayFlattener();
		int[] actual = flattener.flatten(source);
		Assert.assertArrayEquals(expected, actual);
		
	}
	
	@Test
	public void testFlatteningIntegersBitMoreComplex() throws NegativeArraySizeException, IllegalAccessException, InstantiationException {

		Object[] source = new Object[]{
				new Object[] {
						1,
						2,
						new Object[]{3}
				},
				4
		};
		int[] expected = new int[]{ 1,2,3,4 };
		
		ArrayFlattener flattener = new ArrayFlattener();
		int[] actual = flattener.flatten(source);
		Assert.assertArrayEquals(expected, actual);
		
	}
	
	@Test
	public void testFlatteningMoreComplex() throws NegativeArraySizeException, IllegalAccessException, InstantiationException {

		Object[] source = new Object[]{
				1,4, 
				 new int[]{3,4},
				 new Object[][]{
						new Integer[]{3,4,5}  , 
						new Integer[]{8987,765}
						 , 
							new Object[]{
								new Integer[]{8,9,10}  , 
								new Integer[]{11,12},
								new Object[]{
										new Integer[]{13,14,15}  , 
										new Integer[]{16,17}
										},
								new Object[]{
										new int[]{13,14,15}  
										}
								}
						},
						33,
						909
		};
		int[] expected = new int[]{ 1,4,3,4,3,4,5,8987,765,8,9,10,11,12,13,14,15,16,17,13,14,15,33,909 };
		
		ArrayFlattener flattener = new ArrayFlattener();
		int[] actual = flattener.flatten(source);
		Assert.assertArrayEquals(expected, actual);
		
	}

}
