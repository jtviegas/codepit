package org.aprestos.labs.snippets.impl.lang.inheritancewrapping;

import java.util.List;

public class Node {
	
	protected String name;
	protected List<Node> chldren;
	
	public Node(){}
	
	public Node(String name){
		this.name=name;
	}
}
