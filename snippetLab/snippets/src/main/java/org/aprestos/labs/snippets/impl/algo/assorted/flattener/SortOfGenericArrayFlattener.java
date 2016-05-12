package org.aprestos.labs.snippets.impl.algo.assorted.flattener;

import java.lang.reflect.Array;

public class SortOfGenericArrayFlattener<T> {

	public static int ARRAY_SECTION_LENGTH = 16;
	private Class<? extends T> clazz;

	public SortOfGenericArrayFlattener(Class<? extends T> clazz){
		this.clazz = clazz;
	}
	
	@SuppressWarnings("unchecked")
	public T[] flatten(Object[] source) throws NegativeArraySizeException, IllegalAccessException, InstantiationException{

			
		T[] result = (T[]) Array.newInstance(clazz, ARRAY_SECTION_LENGTH);
		int offset = 0;
	
		for(Object o : source){
			
			if(o.getClass().isArray()) {
				//if this object is an array then recurse into this same method
				T[] toAdd = flatten((Object[])o);
				//check if we have to expand the array
				int newOffset = offset + toAdd.length;
				while(result.length <= newOffset){
					T[] old = result;
					result = (T[]) Array.newInstance(clazz , old.length + ARRAY_SECTION_LENGTH);
					System.arraycopy(old, 0, result, 0, old.length);
				}
				//merge the results
				System.arraycopy(toAdd, 0, result, offset, toAdd.length);
				offset = newOffset;
			}
			else { //not an array
				//but correct type
				if(result.getClass().getComponentType().isAssignableFrom(o.getClass())) {
					//is the array long enough
					if(result.length <= offset){
						T[] old = result;
						result = (T[]) Array.newInstance(clazz , old.length + ARRAY_SECTION_LENGTH);
						System.arraycopy(old, 0, result, 0, old.length);
					}
					result[offset++] = (T)o;
				}
				//wrong type
				else
					System.out.println("Wrong Type, will discard this element");
			}
		}
		
		//trim the result array
		T[] old = result;
		result = (T[]) Array.newInstance(clazz , offset);
		System.arraycopy(old, 0, result, 0, offset);
		
		return result;
	}
		
	

}
