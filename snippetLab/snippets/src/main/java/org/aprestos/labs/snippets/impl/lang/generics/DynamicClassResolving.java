package org.aprestos.labs.snippets.impl.lang.generics;

import java.util.ArrayList;
import java.util.List;

public class DynamicClassResolving {

	public DynamicClassResolving() {
		
	}
	
	public List<Letter> sortByType(List<Letter> bundle, Class<? extends Letter> t){
		
		List<Letter> result = new ArrayList<Letter>();
		
		for(Letter g:bundle)
			if(t.isAssignableFrom(g.getClass()))
				result.add(g);
		
		return result;
		
	}
	

	

}
