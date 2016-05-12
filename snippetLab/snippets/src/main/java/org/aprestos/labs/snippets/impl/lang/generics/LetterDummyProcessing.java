package org.aprestos.labs.snippets.impl.lang.generics;

public class LetterDummyProcessing<T extends Letter> {

	Class<?> clazz;
	public LetterDummyProcessing(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public void processing( T t){
		System.out.println("class: " + clazz.getName());
		t.getClass();
		t.write();
	}
	

}
