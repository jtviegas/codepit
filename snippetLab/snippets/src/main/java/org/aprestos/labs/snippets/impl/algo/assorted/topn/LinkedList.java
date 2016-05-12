package org.aprestos.labs.snippets.impl.algo.assorted.topn;


public class LinkedList {

	LinkedList first;
	int value = -1;
	LinkedList next;
	
	public LinkedList() {}
	
	public LinkedList(int value) {
		this.value = value;
	}
	
	public LinkedList add(int childValue) {
		
		if(-1 == this.value){
			this.value = childValue;
			this.first = this;
			return this;
		}
		else {
			LinkedList child = new LinkedList(childValue);
			this.next = child;
			child.first = this.first;
			return child;	
		}
		
	}
	
	public static LinkedList subSet(LinkedList from, int size){
		
		LinkedList result = null;
		int index = 0 ;
		LinkedList list = from;
		if(null != list)
		{
			result = new LinkedList();
			result = result.add(list.value);
			index++;
			while(null != list.next){
				result = result.add(list.next.value);
				index++;
				list = list.next;
				if(index == size)
					break;
			}
		}
		return result.first;
	}
	
	public static LinkedList customMerge(LinkedList destin, LinkedList list, int maxSize){
		
		//int index = 0;
		int index = 0;
		LinkedList a = destin.first;
		LinkedList b = list.first;
		
		LinkedList result = new LinkedList();
		
		while(true){
			
			if(null == a){
				if(null == b){
					break;
				}
				else {
					result = result.add(b.value);
					index++;
					b = b.next;
				}
			} 
			else {
				if(null == b){
					result = result.add(a.value);
					index++;
					a = a.next;
				}
				else {
					if(a.value >= b.value){
						result = result.add(a.value);
						index++;
						a = a.next;
					}
					else {
						result = result.add(b.value);
						index++;
						b = b.next;
					}		
				}
			}
			
			if(maxSize <= index)
				break;
			
		}
		
		return result.first;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinkedList other = (LinkedList) obj;
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		sb.append(this.value);
		if(null != next)
			sb.append( " | " + next.toString());
		
		return sb.toString();
	}



}
