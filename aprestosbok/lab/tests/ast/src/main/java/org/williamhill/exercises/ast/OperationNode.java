package org.williamhill.exercises.ast;

public class OperationNode implements Node 
{
	private Node left;
	private Node right;
	private String operand;
	
	public OperationNode() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public OperationNode(String _operand, Node _left, Node _right) 
	{
		this.operand = _operand;
		this.left = _left;
		this.right = _right;
	}



	public int evaluate()
	{
		int _result = 0;
		
		if (operand.equals("+"))
			_result = left.evaluate() + right.evaluate();
		
		if (operand.equals("-"))
			_result = left.evaluate() - right.evaluate();
		
		if (operand.equals("*"))
			_result = left.evaluate() * right.evaluate();
		
		if (operand.equals("/"))
			_result = left.evaluate() / right.evaluate();
			
		return _result;
	}
	

	
}
