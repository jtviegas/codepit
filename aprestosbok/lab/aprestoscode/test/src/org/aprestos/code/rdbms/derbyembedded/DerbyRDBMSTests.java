package org.aprestos.code.rdbms.derbyembedded;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.aprestos.code.logger.LoggerFactory;
import org.aprestos.code.logger.interfaces.LoggerInterface;
import org.aprestos.code.rdbms.AbstractRDBMSFactory;
import org.aprestos.code.rdbms.interfaces.RDBMSFactoryInterface;
import org.aprestos.code.rdbms.interfaces.RDBMSInterface;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class DerbyRDBMSTests extends TestCase
{
	private Properties props;
	private RDBMSFactoryInterface f;
	private RDBMSInterface o;
	
	private static LoggerInterface logger;
	
	public static Test suite()
	{
		return new TestSuite(DerbyRDBMSTests.class);
	}
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		InputStream is = this.getClass().getResourceAsStream("DerbyRDBMSTests.properties");
		props = new Properties();
		props.load(is);
		is.close();
		is=null;
		LoggerFactory.initialize(props);
		logger = LoggerFactory.getInstance().getLogger(DerbyRDBMSTests.class);
		f = AbstractRDBMSFactory.getFactory(props);
		
		f.initialize();
		o = f.getRDBMS();
		
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		props = null;
		f=null;
		o=null;
	}

	public void testConnectionClosed()
	{
		try
		{
			assertFalse(o.isConnectionOpen());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	public void testConnectionOpen()
	{
		try
		{
			o.openConnection();
			
			assertTrue(o.isConnectionOpen());
			
			o.closeConnection();
			
			assertFalse(o.isConnectionOpen());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	public void testGetResultSet()
	{
		try
		{
			o.openConnection();
			
			assertTrue(o.isConnectionOpen());
		
			Connection c = o.getConnection();
			
			String value = "name";
			
			PreparedStatement ps_i = c.prepareStatement("insert into parent(name)values(?)", 
					Statement.RETURN_GENERATED_KEYS);
			ps_i.setString(1, value);
			ps_i.execute();
			ResultSet rs_i = ps_i.getGeneratedKeys();
			rs_i.next();
			int id = rs_i.getInt(1);

			PreparedStatement ps_s = c.prepareStatement("select name from parent where id=?");
			ps_s.setInt(1, id);
			ResultSet rs_s = ps_s.executeQuery();
			rs_s.next();
			String name = rs_s.getString(1);
			assertEquals(value, name);
			
			PreparedStatement ps_d = c.prepareStatement("delete from parent where id=?");
			ps_d.setInt(1, id);
			ps_d.execute();
			
			boolean existsRecord = false;
			rs_s = ps_s.executeQuery();
			if(rs_s.next())
				existsRecord = true;
			
			assertFalse(existsRecord);
			
			o.closeConnection();
			assertFalse(o.isConnectionOpen());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void testTransaction()
	{
		try
		{
			o.openConnection();
			
			assertTrue(o.isConnectionOpen());
			Connection c = o.getConnection();
			
			c.setAutoCommit(false);
			
			//let's insert a value
			String value = "name";
			PreparedStatement ps_i = c.prepareStatement("insert into parent(name)values(?)", 
					Statement.RETURN_GENERATED_KEYS);
			ps_i.setString(1, value);
			ps_i.execute();
			ResultSet rs_i = ps_i.getGeneratedKeys();
			rs_i.next();
			int id = rs_i.getInt(1);

			//let's see if it exists
			PreparedStatement ps_s = c.prepareStatement("select name from parent where id=?");
			ps_s.setInt(1, id);
			ResultSet rs_s = ps_s.executeQuery();
			boolean exists = rs_s.next();
			
			assertEquals(true, exists);

			//now we are going to commit and check it all again
			c.commit();
			c.setAutoCommit(true);
			
			rs_s = ps_s.executeQuery();
			exists = rs_s.next();
			
			assertEquals(true, exists);
			
			PreparedStatement ps_d = c.prepareStatement("delete from parent where id=?");
			ps_d.setInt(1, id);
			ps_d.execute();
			
			
			o.closeConnection();
			assertFalse(o.isConnectionOpen());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void testFaultyTransaction()
	{
		int id=0;
		PreparedStatement ps_s = null;
		
		try
		{
			o.openConnection();
			
			assertTrue(o.isConnectionOpen());
			Connection c = o.getConnection();
			
			c.setAutoCommit(false);

			try
			{
				//let's insert a value
				String value = "name";
				PreparedStatement ps_i = c.prepareStatement("insert into parent(name)values(?)", 
						Statement.RETURN_GENERATED_KEYS);
				ps_i.setString(1, value);
				ps_i.execute();
				ResultSet rs_i = ps_i.getGeneratedKeys();
				rs_i.next();
				id = rs_i.getInt(1);

				//let's see if it exists
				ps_s = c.prepareStatement("select name from parent where id=?");
				ps_s.setInt(1, id);
				ResultSet rs_s = ps_s.executeQuery();
				boolean exists = rs_s.next();
				
				assertEquals(true, exists);

				//let's insert a value in another table
				PreparedStatement ps_i2 = c.prepareStatement("insert into son(n,parent_id,sn)values(?,?,?)", 
						Statement.RETURN_GENERATED_KEYS);
				ps_i2.setString(1, "n");
				ps_i2.setInt(2, id + 1000); //we are going to generate an error
				ps_i2.setString(3, "sn");
				ps_i2.execute();
				
				//now we are going to commit and check it all again
				c.commit();
				c.setAutoCommit(true);

			}
			catch (SQLException e)
			{
				c.rollback();
				c.setAutoCommit(true);
			}
			
			ResultSet rs = ps_s.executeQuery();
			assertFalse(rs.next());
			
			o.closeConnection();
			assertFalse(o.isConnectionOpen());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}