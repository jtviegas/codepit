package org.aprestos.labs.snippets.impl.lang.classCloning;


public class Suv extends Vehicle {
	
	private int traction;
	
	public Vehicle cloneStructure() throws IllegalAccessException, InstantiationException {
		Suv obj = (Suv) super.cloneStructure();
		obj.traction = this.traction;
		return obj;
	}
}