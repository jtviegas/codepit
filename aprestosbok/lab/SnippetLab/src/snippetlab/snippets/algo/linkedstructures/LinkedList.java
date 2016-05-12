package snippetlab.snippets.algo.linkedstructures;

public class LinkedList
{
	int data = -1;
	LinkedList next;

	
	public String toString()
	{
		return stringify();
	}
	
	String stringify()
	{
		String _b = " | " + data;
		if(null != next)
			_b += next.toString();
		
		return _b;
	}
	
	public LinkedList(){}
	
	public LinkedList(int data)
	{
		this.data = data;
	}
	
	public Object clone()
	{
		LinkedList _o = new LinkedList();
		
		_o.data = this.data;
		
		if(null != this.next)
			_o.next = (LinkedList) this.next.clone();
		
		return _o;
	}
	/**
	 * adds an item to the beginning of the list
	 * 
	 * @param data
	 * @param list
	 * @return
	 */
	public static LinkedList add(int data, LinkedList list)
	{
		LinkedList _o = null;
		
		if(null == list)
			return new LinkedList(data);
		
			_o = new LinkedList(data);
			_o.next = list;
			return _o;
		
	}
	
	public static LinkedList search(LinkedList list, int data)
	{
		if(data == list.data)
			return list;
		else
			return LinkedList.search(list.next, data);
	}

	public static void traverse(LinkedList list)
	{
		System.out.print( " | " + list.data);
		LinkedList.traverse(list.next);
		
	}
	
	
	public static LinkedList predecessor(LinkedList list, int _o)
	{
		if(null == list.next)
			return null;
		
		if(_o == list.next.data)
			return list;
		else
			return predecessor(list.next, _o);

	}
	
	public static LinkedList sucessor(LinkedList list, int _o)
	{
		if(list.data == _o)
		{
			if(null == list.next)
				return null;
			else
				return list.next;
		}
		else
			if(null == list.next)
				return null;
			else
				return sucessor(list.next,_o);

	}
	
	public static LinkedList delete(LinkedList list, int _o)
	{
		
		if (null==list )
			return null;
		
		LinkedList _oo = LinkedList.search(list, _o);
		LinkedList _predecessor = LinkedList.predecessor(list, _oo.data);
		LinkedList _sucessor = _oo.next;
		
		if (null == _predecessor)
				return _sucessor;
		
		_predecessor.next = _sucessor;
			
		return list;
	}
	
	
	
	public static LinkedList invert(LinkedList list, LinkedList dest)
	{
			dest = LinkedList.add(list.data, dest);
			if(null != list.next)
				 return invert( list.next, dest);
		
			return dest;
	}
	
	
	
	
}
