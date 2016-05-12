package org.aprestos.labs.jee.jeepromenade_persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.aprestos.labs.jee.jeepromenade_persistence.retail.UnitOfMeasure;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	private EntityManagerFactory emf;
	private static EntityManager em;
	private EntityTransaction tx;
	
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

    
    public void testPersistUnrelatedObject()
    {
    	try
		{
    		UnitOfMeasure _um = new UnitOfMeasure("kg");
    		tx.begin();
    		
			em.persist(_um);
			assertTrue(_um.getId() > 0);
			
			tx.commit();
			
		} catch (Exception e)
		{
			e.printStackTrace();
			tx.rollback();
		}
    	
    }

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */

	protected void setUp() throws Exception
	{
		//specify the desired persistence unit
		emf = Persistence.createEntityManagerFactory("jeepromenade");
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */

	protected void tearDown() throws Exception
	{
		em.close();
		emf.close();
	}
    
    
    
}
