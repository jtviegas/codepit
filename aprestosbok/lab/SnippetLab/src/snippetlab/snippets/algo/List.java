package snippetlab.snippets.algo;

public class List
{
	int container;
	List next;
	
	List(int element, List tail)
	{
		this.container = element;
		this.next = tail;
	}
	
	List insert(int element)
	{
		return new List(element, this);
	}
	
	void Display()
	{
		List _u = this;
		
		while(null != _u)
		{
			System.out.print(_u.container + "-->");
			_u = _u.next;
		}
		System.out.println("null");
	}
}
