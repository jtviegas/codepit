/*
 * TransactionScriptTest.java Copyright (C) Wincor Nixdorf.
 */
package org.aprestos.code.patterns;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.aprestos.code.exceptions.WrongArgumentException;
import org.aprestos.code.logger.LoggerFactory;
import org.aprestos.code.misc.Money;
import org.aprestos.code.misc.SimpleDate;
import org.aprestos.code.patterns.transactionscript.DataGateway;
import org.aprestos.code.patterns.transactionscript.RecognitionServiceTS;
import org.aprestos.code.rdbms.AbstractRDBMSFactory;
import org.aprestos.code.rdbms.exceptions.RDBMSConnectionException;
import org.aprestos.code.rdbms.interfaces.RDBMSFactoryInterface;
import org.aprestos.code.rdbms.interfaces.RDBMSInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TransactionScriptTest
{
	private static final String PROPERTIES_SUFFIX = ".properties";
	private DataGateway gw;
	private RecognitionServiceTS ts;

	
	@Test
	public void exampleTest()
	{
		try
		{
			ts.calculateRevenueRecognitions(1);
			Money revenue = ts.recognizeRevenue(1, new SimpleDate(2009,04,26));
			System.out.println("revenue is " + revenue.amount());
			
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (RDBMSConnectionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (WrongArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Before
	public void prepareTest()
	{
		try
		{
				Properties props = null;
				String[] classname = this.getClass().getName().split("\\.");
				
				InputStream is = this.getClass().getResourceAsStream(classname[classname.length - 1]
						+ PROPERTIES_SUFFIX);
				
				props = new Properties();
				props.load(is);
				
				is.close();
				is = null;

			LoggerFactory.initialize(props);
				
			RDBMSFactoryInterface f = AbstractRDBMSFactory.getFactory(props);
			f.initialize();
			RDBMSInterface o = f.getRDBMS();
			
			gw = new DataGateway();
			gw.setRDBMS(o);
			gw.insertData();
			
			ts = new RecognitionServiceTS();
			ts.setGw(gw);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@After
	public void cleanupTest()
	{
		try
		{
			gw.clearData();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (RDBMSConnectionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ts = null;
		gw = null;
	}

}
