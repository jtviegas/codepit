package org.aprestos.labs.snippets.impl.effective;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SingletonTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
	
	Thread t1 = new Thread(new Th());
	Thread t2 = new Thread(new Th());
	
	t1.start();
	t2.start();
	assertTrue(true);
	
    }
    
    private static class Th implements Runnable {

	public void run() {
	    System.out.println(SingletonEnum.INSTANCE.toString());
	    System.out.println(SingletonEnum.INSTANCE.get());
	}
	
    }

}
