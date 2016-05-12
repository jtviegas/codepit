package snippetlab.snippets.algo;

import snippetlab.snippets.AbstractSnippet;

public class LinkedLists extends AbstractSnippet
{
	
	@Override
	public void method()
	{
		doExperiment();
	}
	

	private static void doExperiment()
	{
		System.out.println("/* merge ordered linked lists */");
		mergeOrderedLinkedLists();
		
		System.out.println("/* sort linked lists */");
		sortList();
		
		LinkedList _ll1 = new LinkedList();
		_ll1.add(4);
		_ll1.add(3);
		_ll1.add(2);
		_ll1.add(1);
		
		_ll1.display();
		
		LinkedList _ll2 = LinkedList.reverseRecursively(_ll1);
		_ll2.display();
		
		LinkedList _ll3 = new LinkedList();
		_ll3.add(4);
		_ll3.add(3);
		_ll3.add(2);
		_ll3.add(1);
		
		
		LinkedList _ll4 = LinkedList.reverseIteratively(_ll3);
		_ll4.display();
		
		
	}
	
	
	private static void mergeOrderedLinkedLists()
	{
		List _l1 = new List(14, null);_l1=_l1.insert(12);_l1=_l1.insert(8);_l1=_l1.insert(4);
		List _l2 = new List(13, null);_l2=_l2.insert(11);_l2=_l2.insert(7);_l2=_l2.insert(3);
		_l1.Display();
		_l2.Display();
		
		List _o = mergeOrderedLists(_l1,_l2);
		_o.Display();
		
		
	}
	
	private static void sortList()
	{
		List _l1 = new List(4, null);_l1=_l1.insert(32);_l1=_l1.insert(8);_l1=_l1.insert(1);
		_l1.Display();
		sort(_l1).Display();
		
		
	}
	
	private static List sort(List _list)
	{
		int _len = getLength(_list);
		
		if(1 >= _len)
			return _list;
		else
		{
			int _left,_middle;
			List _o1,_o2, _split,_prevSplit;

			_o1 = _list; //take the list
			_prevSplit = _split = _list; //no splits yet
			_left = 0;//set left index
			_middle = _len/2;//set middle index
			
			//split the list in two both with null at the end
			while(_left < _middle)
			{
				_left++;
				_prevSplit = _split;
				_split = _split.next;
			}
			
			_o2 = _split;
			_prevSplit.next = null;
			
			//sort it recursively till you have only two elements
			//which then will be sorted by the merge method
			
			return mergeOrderedLists(sort(_o1),sort(_o2));
		}
		
		
	}
	
	private int string2Integer(String _s)
	{
		int _result = 0;
		for(int _j=0;_j<_s.length();_j++)
			_result = _result*31+_s.charAt(_j);
		
		return _result;
	}
	
	private static int getLength(List _l)
	{
		if(null != _l)
			return 1 + getLength(_l.next);
		else
			return 0;
		
	}
	
	private static List mergeOrderedLists(List _a, List _b)
	{
		if(null == _a) return _b;
		if(null == _b) return _a;
		
		if(_a.container<_b.container)
		{
			_a.next = mergeOrderedLists(_a.next, _b);
			return _a;
		}
		else
		{
			_b.next=mergeOrderedLists( _b.next,_a);
			return _b;
		}
		
	}
	
	
	private static void swap(int[] _a, int _i , int _j)
	{
		int _tmp=_a[_i];
		_a[_i] = _a[_j];
		_a[_j] = _tmp;
	}

	private static String intArrayDisplay(int[] _a)
	{
		StringBuffer _s = new StringBuffer("|");
		for(int _i:_a)
		{
			_s.append(_i + "|");
		}
		
		return _s.toString();
	}
	
	

}

class Node
{
	public Object data;
	public Node next;

	public Node(Object data, Node next)
	{
		this.data = data;
		this.next = next;
	}
	
	public Node add(Object _o)
	{
		return new Node(_o, this);
	}
}

class LinkedList
{
	public Node head = null;

	
	public void add(Object _o)
	{
		this.head= new Node(_o, this.head);
	}
	
	public void display()
	{
		 Node _node = head;
		 while(null != _node)
		 {
			 System.out.print(_node.data + "-->");
			 _node=_node.next;
		 }
		 
		 System.out.println("null");
	}

	public static LinkedList reverseIteratively(LinkedList _ll)
	{
		LinkedList _result = new LinkedList();
		Node _node = _ll.head;

		if (null != _node)//last one
			_result.head = new Node(_node.data, null);
		
		while(null != _node.next)
		{
			_node = _node.next;
			_result.head = new Node(_node.data, _result.head);
		}
		return _result;
	}

	public static LinkedList reverseRecursively(LinkedList _ll)
	{
		LinkedList _result = new LinkedList();
		
		_result.head=reverse(_ll.head);
		
		return _result;
	}
	
	static Node reverse(Node _a)
	{
		Node _result = null;
		
		if(_a == null || _a.next == null)
		{
			//can't reverse this one
			return _a;
		}
		
		_result = reverse(_a.next);
		_a.next.next=_a;
		_a.next=null;
		
		return _result;	
	}
	

}
