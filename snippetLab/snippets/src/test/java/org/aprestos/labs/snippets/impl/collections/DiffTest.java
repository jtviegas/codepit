package org.aprestos.labs.snippets.impl.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class DiffTest {


	
	@Test
	public void simpleTest1() throws Exception {
	    
		
	    List<String> o = new ArrayList<>();
	    
	    o.add("a"); 
	    o.add("b"); 
	    o.add("c");
	    
	    List<String> o2 = new ArrayList<>();
	    
	    o2.add("c"); 
	    o2.add("b"); 
	    o2.add("a");
	    
	    
	    Assert.assertEquals(o, o2);
	   
	}
	


	class SimpleBean {
		
		private int n;
		private String desc;
		
		SimpleBean(int n, String desc){
			this.n=n;
			this.desc=desc;
		}
		public int getN() {
			return n;
		}
		public void setN(int n) {
			this.n = n;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(desc, n);
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
			SimpleBean other = (SimpleBean) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(desc, other.desc) && n == other.n;
		}
		private DiffTest getEnclosingInstance() {
			return DiffTest.this;
		}

	}
}
