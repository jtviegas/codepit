/*
 * AbstractRDBMSFactoryTest.java
 */
/**
 * 
 */
package snippetlab.snippets.misc.DerbyDigressions.rdbms;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.aprestos.code.bok.logger.LoggerFactory;
import org.aprestos.code.bok.rdbms.AbstractRDBMSFactory;
import org.aprestos.code.bok.rdbms.exceptions.RDBMSInitializeException;
import org.aprestos.code.bok.rdbms.interfaces.RDBMSFactoryInterface;
import org.aprestos.code.bok.rdbms.interfaces.RDBMSInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 */
public class AbstractRDBMSFactoryTest
{

    private RDBMSFactoryInterface factory;
    private String file;
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
	InputStream inStream = AbstractRDBMSFactoryTest.class.getResourceAsStream(
		"/snippetlab/snippets/misc/DerbyDigressions/rdbms/rdbms.properties");
	Properties props = new Properties();
	props.load(inStream);
	
	file = props.getProperty("derby.embedded.rdbms.folder") + System.getProperty("file.separator") + 
		props.getProperty("db.name");
	
	LoggerFactory.initialize(props);
	
	factory = AbstractRDBMSFactory.getFactory(props);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
	
	factory=null;
	deleteDir(new File(file));
	file=null;
    }

    /**
     * Test method for {@link org.aprestos.code.bok.rdbms.AbstractRDBMSFactory#getRDBMS()}.
     */
    @Test
    public void testGetRDBMS()
    {
	try
	{
	    factory.initialize();
	    org.junit.Assert.assertTrue((new File(file)).exists());
	}
	catch(RDBMSInitializeException ie)
	{
	    org.junit.Assert.fail(ie.getMessage());
	}
	
	RDBMSInterface rdbms = factory.getRDBMS();
	org.junit.Assert.assertNotNull(rdbms);
	
	
    }
    
    @Test
    public void testConnection()
    {

	    try
	    {
		factory.initialize();
		    RDBMSInterface rdbms = factory.getRDBMS();
		    
		    if(!rdbms.isConnectionOpen())
		    {
			rdbms.openConnection();
		    }
		    
		    org.junit.Assert.assertEquals(true, rdbms.isConnectionOpen());
		    
		    rdbms.closeConnection();
		    
		    org.junit.Assert.assertEquals(false, rdbms.isConnectionOpen());
		    
	    }
	    catch(Exception x)
	    {
		org.junit.Assert.fail(x.getMessage());
	    }

    }
    

    public void doASelect()
    {
	try
	    {
		factory.initialize();
		
		    RDBMSInterface rdbms = factory.getRDBMS();
		    
		    if(!rdbms.isConnectionOpen())
		    {
			rdbms.openConnection();
		    }
		    
		    org.junit.Assert.assertEquals(true, rdbms.isConnectionOpen());
		    
		    Connection c = rdbms.getConnection();
		    
		    org.junit.Assert.assertNotNull(c);
		    
		    rdbms.closeConnection();
		    
		    org.junit.Assert.assertEquals(false, rdbms.isConnectionOpen());
		    
		    
		    
	    }
	    catch(Exception x)
	    {
		org.junit.Assert.fail(x.getMessage());
	    }
    }
    
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
    
        // The directory is now empty so delete it
        return dir.delete();
    }

}
