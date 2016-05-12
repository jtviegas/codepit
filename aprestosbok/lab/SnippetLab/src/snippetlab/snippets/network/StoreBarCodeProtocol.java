/**
 * StoreBarCodeProtocol.java Copyright (C)Wincor-Nixdorf Portugal
 * 2010/09/21 00:17:12
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

/**
 * StoreBarCodeProtocol
 * ...description... 
 *
 * @see
 * @since
 */
public class StoreBarCodeProtocol implements TCPProtocol
{
	private static final String STORE_MSG="<STORE>5601009936168</STORE>";
	private static final String STORE_MSG2="<REQ>GETNEXTEXITGATEBARCODE</REQ>";
	
	/**
	 * @see snippetlab.snippets.network.TCPProtocol#go(java.io.InputStream, java.io.OutputStream)
	 */
	@Override
	public void go(InputStream _in, OutputStream _out) throws ProtocolException
	{
		String _response=null;
		
		try
		{
			PrintWriter _outw = new PrintWriter(_out,true);
			BufferedReader _inr = new BufferedReader(new InputStreamReader(_in));
			
			_outw.write(STORE_MSG2);
			System.out.println("sent to the server: " + STORE_MSG);
			_response = _inr.readLine();
			System.out.println("received from the server: " + _response);
		}
		catch (IOException e)
		{
			throw new ProtocolException(e);
		}
		
	}

}
