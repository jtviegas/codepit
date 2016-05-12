package snippetlab.snippets.algo.linkedstructures;

public class Stack
{
	private Stack next;
	private int data;
	
	public Stack(){}
	
	public Stack(int _data)
	{
		this.data = _data;
	}
	
	public void push(int _data)
	{
		if(0 == data)
			this.data = _data;
		else
		{
			Stack _s = (Stack) this.clone();
			
			this.data = _data;
			this.next = _s;
		}
	}
	
	public int pop()
	{
		int _r = this.data;
		
		this.data = next.data;
		this.next = next.next;
		
		return _r;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + data;
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stack other = (Stack) obj;
		if (data != other.data)
			return false;
		if (next == null)
		{
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next))
			return false;
		return true;
	}

	public Object clone()
	{
		Stack _o = new Stack();
		_o.data = this.data;
		
		if(null != this.next)
			_o.next = (Stack)this.next.clone();
		
		return _o;
	}
	

	
	public String toString()
	{
		StringBuffer _sb = new StringBuffer();
		if(this.data != 0)
		{	
			_sb.append(this.data);
			if(null != this.next)
				_sb.append(" | " + this.next.toString());
		}
		return _sb.toString();
	}
	
	public static void push(int _data, Stack _s)
	{
		_s.push(_data);
	}
	
	public static int pop(Stack _s)
	{
		return _s.pop();
	}

}
