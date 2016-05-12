/**
 * TCPClientTest.java Copyright (C)Wincor-Nixdorf Portugal
 * 2010/09/21 00:04:13
 * @author	joao.viegas
 *
 * TODO
 * 
 * CHANGES:
 */
package snippetlab.snippets.network;

import snippetlab.snippets.AbstractSnippet;

/**
 * TCPClientTest
 * ...description... 
 *
 * @see
 * @since
 */
public class TCPClientTest extends AbstractSnippet
{

	/**
	 * 
	 */
	public TCPClientTest()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see snippetlab.snippets.AbstractSnippet#method()
	 */
	@Override
	public void method()
	{
		try
		{
			TCPClient _client =  new TCPClient("192.168.3.220",9101,new StoreBarCodeProtocol());
			_client.go();
		}
		catch (TCPException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
