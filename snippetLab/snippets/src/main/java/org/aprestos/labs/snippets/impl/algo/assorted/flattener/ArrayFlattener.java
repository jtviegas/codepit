package org.aprestos.labs.snippets.impl.algo.assorted.flattener;

public class ArrayFlattener {

	public static int ARRAY_SECTION_LENGTH = 16;
	
	
	public int[] flatten(Object source) throws IllegalArgumentException {
		
		int[] result = new int[ARRAY_SECTION_LENGTH];
		Object[] array = null;
		int offset = 0;

		if (source.getClass().isArray()) {
			if (source.getClass().getComponentType().getName().equalsIgnoreCase("int"))
				array = (Integer[]) convertIntArray((int[])source); // primitive array
			else if ( Integer.class.isAssignableFrom(source.getClass().getComponentType()) || Object.class.isAssignableFrom(source.getClass().getComponentType()))
				array = (Object[]) source;// not a primitive array
			else
				throw new IllegalArgumentException("wrong array type! Integer[], int[] or Object[] allowed only !!!");

			for (Object o : array) {
				// flatten every element
				int[] toAdd = flatten(o);
				// check if we have to expand the array
				int newOffset = offset + toAdd.length;
				// expand if required
				result = assureArraySize(newOffset, result);
				// merge the results
				System.arraycopy(toAdd, 0, result, offset, toAdd.length);
				offset = newOffset;
			}
		}
		else if (!Integer.class.isAssignableFrom(source.getClass()))
			throw new IllegalArgumentException("wrong array type! Integer[], int[] or Object[] allowed only !!!");
		else { // expand if required and merge
			result = assureArraySize(offset, result);
			// lets make faith on boxing/unboxing
			result[offset++] = (Integer) source;
		}
		
		return trimArray(offset,result);
	}

	private int[] trimArray(int size, int[] source){
		int[] old = source;
		source = new int[size];
		System.arraycopy(old, 0, source, 0, size);
		return source;
	}
	
	private int[] assureArraySize(int size, int[] sourceArray){
		while (sourceArray.length <= size) {
			int[] old = sourceArray;
			sourceArray = new int[old.length + ARRAY_SECTION_LENGTH];
			System.arraycopy(old, 0, sourceArray, 0, old.length);
		}
		return sourceArray;
	}
	
	private Integer[] convertIntArray(int[] origin) {
		Integer[] result = new Integer[origin.length];
		
		int index = 0;
		for(int i:origin)
			result[index++]=i;
		
		return result;
	}
}
