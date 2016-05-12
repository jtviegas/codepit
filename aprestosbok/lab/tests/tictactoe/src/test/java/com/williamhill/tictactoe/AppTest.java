package com.williamhill.tictactoe;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{


    /**
     * Rigourous Test :-)
     * @throws InvalidAssignmentException 
     * @throws IllegalArgumentException 
     */
	@Test
    public void testApp() throws IllegalArgumentException, InvalidAssignmentException
    {
		IBoard one = new BoardImpl();
		
		one.setCell(1, 2, Signs.Circle);
		one.setCell(2, 2, Signs.Circle);
		
		IBoard two = one.clone();
		
		two.setCell(3, 2, Signs.Cross);
		one.setCell(3, 2, Signs.Circle);
		
		
    	Assert.assertNotSame( one.getCell(3, 2),two.getCell(3, 2) );
    	
    	Assert.assertNotSame(one, two);
    }
	
	
	
	
}
