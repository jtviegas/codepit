/*
 * LoggerFactoryTest.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package org.aprestos.code.bok.logging;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.aprestos.code.bok.common.exceptions.FactoryInitializeException;
import org.aprestos.code.bok.logger.factories.AbstractLoggerFactory;
import org.aprestos.code.bok.logger.interfaces.LoggerInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 */
public class LoggerFactoryTest
{

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }

    /**
     * Test method for {@link org.aprestos.code.bok.logger.factories.AbstractLoggerFactory#getFactory(java.util.Properties)}.
     */
    @Test
    public void testGetFactory()throws IOException, FactoryInitializeException
    {
	Properties p = new Properties();
	p.load(new FileInputStream("test/logger.config"));
	LoggerInterface logger = AbstractLoggerFactory.getFactory(p).getLogger(this.getClass());
	logger.debug("oi");
	logger.enter("jiu", new Object[]{"as","os"});
    }

}
