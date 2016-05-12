package org.williamhill.exercises.ast;

public class ValueNode implements Node {

	private int value;
	
	public ValueNode() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public ValueNode(int _value) 
	{
		this.value = _value;
	}



	public int evaluate()
	{
		return this.value;
	}
}
