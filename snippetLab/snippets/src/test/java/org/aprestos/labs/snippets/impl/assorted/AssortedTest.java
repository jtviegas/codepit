package org.aprestos.labs.snippets.impl.assorted;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class AssortedTest {

    //@Test
    public void test() {

	double state = Double.NaN;
	double[] newvals = { 0, 0.0000000001, 1.0 };
	System.out.println("inital state: " + Double.toString(state));
	System.out.println("inital state parsed from string: "
		+ Double.parseDouble(Double.toString(state)));

	for (double val : newvals) {
	    System.out.println("" + (val > state));
	    System.out.println("" + Double.compare(val, state));
	    if (Double.compare(val, state) > 0)
		System.out.println("new state: " + val);
	}
    }

    @Test
    public void test2() {
	Bird bird = new Pelican();
	bird.fly();
	
	Pelican bird2 = new Pelican();
	bird2.fly();
    }

    abstract class Bird {
	private void fly() {
	    System.out.println("Bird is flying");
	}
    }

    class Pelican extends Bird {
	protected void fly() {
	    System.out.println("Pelican is flying");
	}
    }
    
    @Test
    public void test3() {
	
	int dummy = 13;
	Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	
	Integer dummyCount = null;
	if( null == (dummyCount = map.get(dummy)) ){
	    dummyCount = new Integer(0);
	    map.put(dummy, dummyCount);
	}
	dummyCount++;

	System.out.println("dummy count is: " + map.get(dummy));
	
    }

}
