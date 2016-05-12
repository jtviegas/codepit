/**
 * TCPProtocol.java Copyright (C)Wincor-Nixdorf Portugal
 * 2010/09/20 23:39:47
 * @author	joao.viegas
 *
 * TODO
 * 
 * CHANGES:
 */
package snippetlab.snippets.network;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * TCPProtocol
 * ...description... 
 *
 * @see
 * @since
 */
public interface TCPProtocol
{
	void go(InputStream _in,OutputStream _out) throws ProtocolException;
}
