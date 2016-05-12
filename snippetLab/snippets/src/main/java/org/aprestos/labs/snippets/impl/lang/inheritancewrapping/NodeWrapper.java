package org.aprestos.labs.snippets.impl.lang.inheritancewrapping;

public class NodeWrapper extends Node {

	public NodeWrapper() {
		super();
	}

	public NodeWrapper(String name){
		super(name);
	}
	
	public void add(Node a){
		this.chldren.add(a);
	}

}
