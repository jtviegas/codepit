package org.aprestos.labs.snippets.impl.heapanalysis;

import org.springframework.stereotype.Component;


/*@Component("snippet")*/
public class SnippetImpl implements org.aprestos.labs.snippets.interfaces.Snippet 
{
	private static int LIMIT=100000;
	
	public SnippetImpl(){
		super();
	}
	
	public void go() throws Exception {

		try {
			//loop 10 time
			int i=0;
			A dummy = new A();
			while (true) {
				
				dummy = dummy.copy();
				
				/*if(++i >= LIMIT) 
					break;*/
				if(i++ == LIMIT)
					com.ibm.jvm.Dump.HeapDump();
				
			}
			//com.ibm.jvm.Dump.HeapDump();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally{
			System.out.println("<SnippetImpl.go()");
		}

	}
	
}

class A {
	long x = 9999999999l;
	A parent;
	
	A copy()
	{
		A o = new A();
		o.parent = this;
		
		return o;
	}
}
