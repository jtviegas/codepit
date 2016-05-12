package org.aprestos.labs.snippets.impl.lang.classCloning;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
	
	protected boolean clonable = true;
	private int storage;
	private String name;
	private List<Vehicle> similars = new ArrayList<Vehicle>();
	
	public Vehicle cloneStructure() throws IllegalAccessException, InstantiationException {
		@SuppressWarnings("unchecked")
		Class<Vehicle> cls = (Class<Vehicle>) this.getClass();
		Vehicle obj = clonable ? cls.newInstance() : this;

		if (clonable) {
			obj.name = this.name;
			obj.storage = this.storage;
			if (similars != null)
				for (int i = 0; i < similars.size(); i++)
					obj.similars.add(similars.get(i).cloneStructure());
		}
		return obj;
	}
	
}