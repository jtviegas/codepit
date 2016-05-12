/*
 * IDocFactoryTest.java Copyright (C) Wincor Nixdorf.
 */
package snippetlab.snippets.wincor_nixdorf.idocparser;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IDocFactoryTest
{
	LineNumberReader pluLineReader; 
	
	@Before
	public void setUp() throws Exception
	{
		InputStream is = IDocFactoryTest.class.getResourceAsStream("WP_PLU");
		pluLineReader = new LineNumberReader(new InputStreamReader(is));
		System.out.println(pluLineReader.ready());
	}

	@After
	public void tearDown() throws Exception
	{
		pluLineReader.close();
		pluLineReader = null;
	}
	
	@Test
	public void goWp_PLU()
	{
		Assert.assertEquals(true, true);
	}
	

}
