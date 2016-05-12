/**
 * TCPClient.java Copyright (C)Wincor-Nixdorf Portugal
 * 2010/09/20 23:00:41
 * @author	joao.viegas
 *
 * TODO
 * 
 * CHANGES:
 */
package snippetlab.snippets.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * TCPClient
 * ...description... 
 *
 * @see
 * @since
 */
public class TCPClient
{
	private String host;
	private int port;
	private TCPProtocol protocol;
	

	public TCPClient()
	{
		// TODO Auto-generated constructor stub
	}
	
	public TCPClient(String _host, int _port, TCPProtocol _protocol)
	{
		this.host=_host;
		this.port = _port;
		this.protocol = _protocol;
	}
	
	/**
	 * @param host the host to set
	 */
	public void setHost(String host)
	{
		this.host = host;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port)
	{
		this.port = port;
	}

	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(TCPProtocol protocol)
	{
		this.protocol = protocol;
	}



	public void go() throws TCPException
	{
		Socket _socket = null;
		try
		{
			//open socket
			try
			{
				_socket = new Socket(this.host,this.port);
			}
			catch (Exception e)
			{
				throw new SocketConnectionException(e);
			}
			//apply protocol
			this.protocol.go(_socket.getInputStream(), _socket.getOutputStream());
		}
		catch (Exception e)
		{
			throw new TCPException(e);
		}
		finally
		{	
			//close socket
			try
			{
				if(null != _socket)
					_socket.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			_socket = null;
		}

		
	}

}
