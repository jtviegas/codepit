/*
 * BasicScaleConnector.java Copyright (C) Wincor Nixdorf.
 */
/**
 * 
 */
package org.aprestos.code.wn.scaleconnector.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import org.aprestos.code.wn.scaleconnector.exceptions.ScaleCommunicationException;
import org.aprestos.code.wn.scaleconnector.exceptions.ScaleConnectException;
import org.aprestos.code.wn.scaleconnector.interfaces.ScaleConnectorInterface;


/**
 * 
 */
public class BasicScaleConnector implements ScaleConnectorInterface
{
	
	private byte[] ip;
	private int port;
		
	private Socket socket;
	private PrintWriter socketWriter;
	private BufferedReader socketReader;
	
	/**
	 * 
	 */
	public BasicScaleConnector()
	{
		// TODO Auto-generated constructor stub
	}

	public BasicScaleConnector(byte[] ip, int port)
	{
		this.ip = ip;
		this.port = port;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(byte[] ip)
	{
		this.ip = ip;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port)
	{
		this.port = port;
	}

	/**
	 * @see org.aprestos.code.wn.scaleconnector.interfaces.ScaleConnectorInterface#open()
	 */
	public void open() throws ScaleConnectException
	{
		try
		{
			socket = new Socket(InetAddress.getByAddress(ip),port);
		}
		catch(IOException ioe)
		{
			throw new ScaleConnectException(ioe);
		}
	}

	/**
	 * @see org.aprestos.code.wn.scaleconnector.interfaces.ScaleConnectorInterface#close()
	 */
	public void close() throws ScaleConnectException
	{
		try
		{
			if(null != socket && !socket.isClosed())
			{
				if(null != socketWriter)
				{
					socketWriter.close();
					socketWriter = null;
				}
				
				if(null != socketReader)
				{
					socketReader.close();
					socketReader = null;
				}
				
				socket.close();
				socket = null;
			}
		}
		catch(IOException ioe)
		{
			throw new ScaleConnectException(ioe);
		}
			
	}

	public String receive() throws ScaleCommunicationException
	{
		try
		{
			StringBuffer inBuffer = new StringBuffer();
			String dummyString = null;
			String result = null;
			
			if(null == socketReader)
				socketReader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

			while ((dummyString = socketReader.readLine()) != null) 
			{
				if(null == inBuffer)
					inBuffer=new StringBuffer();
				
				inBuffer.append(dummyString);
			}
			
			if(null != inBuffer)
				result = inBuffer.toString();
			
			return result;
		}
		catch (Exception e)
		{
			throw new ScaleCommunicationException(e);
		}
	}
	
	/**
	 * @see org.aprestos.code.wn.scaleconnector.interfaces.ScaleConnectorInterface#send(java.lang.String)
	 */
	public void send(String message) throws ScaleCommunicationException
	{
		try
		{
			if(null == socketWriter)
				socketWriter = new PrintWriter(socket.getOutputStream(),true);
			
			socketWriter.println(message);
			
		}
		catch (Exception e)
		{
			throw new ScaleCommunicationException(e);
		}
	}

	public boolean isOpen()
	{
		boolean result = false;
		
		if(	null != socket )
		{
			if(!socket.isClosed() && (socket.isBound() || socket.isConnected()))
			{
				result = true;
			}
		}
	
		return result;
	}




}
