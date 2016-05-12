package org.aprestos.code.labs.jasf;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MainTest
{
    int four = 0;

    @Before
    public void prepareTest()
    {
	four = 4;
    }

    @After
    public void cleanupTest()
    {
	four = 0;
    }

    @Test
    public void BootTestOne()
    {
	Boot _b = new Boot();
	_b.go();
	
	org.junit.Assert.assertEquals(4, four);
    }

}