/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

package org.aprestos.code.rdbms.factories;

import java.util.Properties;

import org.aprestos.code.logger.LoggerFactory;
import org.aprestos.code.logger.interfaces.LoggerInterface;
import org.aprestos.code.rdbms.AbstractRDBMSFactory;
import org.aprestos.code.rdbms.exceptions.RDBMSInitializeException;
import org.aprestos.code.rdbms.impl.DerbyEmbeddedRDBMSImpl;
import org.aprestos.code.rdbms.interfaces.RDBMSInterface;


/**
 * Factory for the <a href="http://db.apache.org/derby/">Derby</a> RDBMS.
 * @author jmv
 */
public class DerbyEmbeddedRDBMSFactory extends AbstractRDBMSFactory
{
	private static LoggerInterface logger = LoggerFactory.getInstance()
			.getLogger(DerbyEmbeddedRDBMSFactory.class);

	private Properties properties;
	private RDBMSInterface rdbms;

	public DerbyEmbeddedRDBMSFactory(Properties properties)
	{
		this.properties = properties;
	}

	/**
	 * @see snippetlab.snippets.misc.DerbyDigressions.rdbms.AbstractRDBMSFactory#initialize()
	 */

	public void initialize() throws RDBMSInitializeException
	{
		logger.enter("initialize");

		try
		{
			rdbms = new DerbyEmbeddedRDBMSImpl(this.properties);
		}
		catch(Exception x)
		{
			throw new RDBMSInitializeException(x);
		}

		logger.leave("initialize");
	}


	/**
	 * @see snippetlab.snippets.misc.DerbyDigressions.rdbms.AbstractRDBMSFactory#getRDBMS()
	 */

	public RDBMSInterface getRDBMS()
	{
		logger.enter("getRDBMS");
		logger.leave("getRDBMS", rdbms);
		return rdbms;
	}


}
