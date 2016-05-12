package snippetlab.snippets.algo.linkedstructures;

import snippetlab.snippets.AbstractSnippet;

//snippetlab.snippets.algo.linkedstructures.Run
public class Run extends AbstractSnippet
{

	@Override
	public void method()
	{
		 LLone();
		 goStacks();
	}
	
	private void goStacks()
	{
		int[] _o = new int[]{2,3,4,77,435,885,32};
		Stack _s = new Stack();
		
		for(int _i = _o.length-1 ; _i >= 0 ; _i--)
		{
			System.out.println(String.format("going to add %d to the stack", _o[_i]));
			_s.push(_o[_i]);
			System.out.println(_s.toString());
		}
		
		System.out.println("stack:");
		System.out.println(_s.toString());
		
		System.out.println(String.format("popping: %d", _s.pop()));
		System.out.println(_s.toString());
		System.out.println(String.format("popping: %d", _s.pop()));
		System.out.println(_s.toString());
		
		
		
	}
	
	private void LLone()
	{
		LinkedList _o = null;
		
		System.out.println("going to search 66 from [ " + getLinkedListA().toString() + " ]");
		_o=LinkedList.search(getLinkedListA(),66);
		System.out.println(_o.toString());
		
		System.out.println("going find the predecessor of 356567 [ " + getLinkedListA().toString() + " ]");
		_o=LinkedList.predecessor(getLinkedListA(),356567);
		System.out.println(_o.toString());
		
		System.out.println("going find the sucessor of 356567 [ " + getLinkedListA().toString() + " ]");
		_o=LinkedList.sucessor(getLinkedListA(),356567);
		System.out.println(_o.toString());
		
		_o=getLinkedListA();
		System.out.println("going to delete 356567 from [ " + _o.toString() + " ]");
		_o = LinkedList.delete(_o,356567);
		System.out.println(_o.toString());
		
		_o = null;
		System.out.println("going invert  [ " + getLinkedListB().toString() + " ]");
		_o = LinkedList.invert(getLinkedListB(),_o);
		System.out.println(_o.toString());
		
		_o = null;
		System.out.println("going invert  [ " + getLinkedListA().toString() + " ]");
		_o = LinkedList.invert(getLinkedListA(),_o);
		System.out.println(_o.toString());
	}
	
	
	private LinkedList getLinkedListA()
	{
		LinkedList _r = null;
		int[] _o = new int[]{2,3,4,77,435,885,32,356567,76,445,30,3453,66};
		
		for(int _i = _o.length-1 ; _i >= 0 ; _i--)
		{
			_r = LinkedList.add(_o[_i], _r);
		}

		return _r;
	}
	
	private LinkedList getLinkedListB()
	{
		LinkedList _r = null;
		int[] _o = new int[]{2,4,1};
		
		for(int _i = _o.length-1 ; _i >= 0 ; _i--)
		{
			_r = LinkedList.add(_o[_i], _r);
		}

		return _r;
	}

}
