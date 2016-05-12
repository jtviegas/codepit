package org.aprestos.code.scaleconnector;

import java.net.InetAddress;

import org.aprestos.code.wn.scaleconnector.ScaleConnectorFactory;
import org.aprestos.code.wn.scaleconnector.exceptions.ScaleCommunicationException;
import org.aprestos.code.wn.scaleconnector.exceptions.ScaleNotImplementedException;
import org.aprestos.code.wn.scaleconnector.impl.BasicScaleConnector;
import org.aprestos.code.wn.scaleconnector.interfaces.ScaleConnectorInterface;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class ScaleConnectorTests extends TestCase
{
	
	ScaleConnectorInterface o;
	
	public static Test suite()
	{
		return new TestSuite(ScaleConnectorTests.class);
	}
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		byte[] ip = new byte[]{(byte)172,(byte)18,(byte)194,(byte)247};
		int port = 3000;
		System.out.println(InetAddress.getLocalHost().getAddress());
		o = new BasicScaleConnector(ip,port);
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		o=null;
	}

		
//	public void testConnectionOpen()
//	{
//		try
//		{
//			assertFalse(o.isOpen());
//			o.open();
//			assertTrue(o.isOpen());
//			o.close();
//			assertFalse(o.isOpen());
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//
//	}
//
//	public void testSendMessage()
//	{
//		try
//		{
//			assertFalse(o.isOpen());
//			o.open();
//			assertTrue(o.isOpen());
//			
//			o.send("");
//			o.close();
//			
//			try
//			{
//				o.send("");
//			}
//			catch(ScaleCommunicationException x)
//			{
//				x.printStackTrace();
//			}
//			
//			o.open();
//			assertTrue(o.isOpen());
//			o.close();
//			assertFalse(o.isOpen());
//			
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//
//	}
//	
//	public void testFactory1()
//	{
//		try
//		{
//			ScaleConnectorInterface sc = ScaleConnectorFactory.getScaleConnector("");
//			assertEquals(BasicScaleConnector.class.getName(), sc.getClass().getName());
//			
//			sc = ScaleConnectorFactory.getScaleConnector("default");
//			assertEquals(BasicScaleConnector.class.getName(), sc.getClass().getName());
//			
//			try
//			{
//				sc = ScaleConnectorFactory.getScaleConnector("eusebio");
//			}
//			catch(ScaleNotImplementedException nie)
//			{
//				nie.printStackTrace();
//			}
//
//			sc=null;
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//
//	}
	
	

}