package org.williamhill.exercises.ast;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testExampleTest()
    {
    	int expected = 19;
    	Node _multiplyNode = new OperationNode("*", new ValueNode(6), new ValueNode(3));
    	Node _addNode = new OperationNode("+", _multiplyNode, new ValueNode(1));
    	
     	assertEquals(expected, _addNode.evaluate());
        
    }

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception 
	{
		
	}
    
    
    
}
