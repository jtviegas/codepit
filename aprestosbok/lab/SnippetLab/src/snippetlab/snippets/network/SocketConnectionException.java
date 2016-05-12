/**
 * NoSocketConnectionException.java Copyright (C)Wincor-Nixdorf Portugal
 * 2010/09/20 23:15:56
 * @author	joao.viegas
 *
 * TODO
 * 
 * CHANGES:
 */
package snippetlab.snippets.network;

/**
 * NoSocketConnectionException
 * ...description... 
 *
 * @see
 * @since
 */
public class SocketConnectionException extends TCPException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SocketConnectionException()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public SocketConnectionException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public SocketConnectionException(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SocketConnectionException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
