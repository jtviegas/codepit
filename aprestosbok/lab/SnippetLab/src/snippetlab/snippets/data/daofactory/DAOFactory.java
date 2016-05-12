/*
 * DAOFactory.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.data.daofactory;

import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import snippetlab.snippets.data.daofactory.exceptions.DAOException;
import snippetlab.snippets.data.daofactory.interfaces.DAOInterface;

/**
 * 
 */
public abstract class DAOFactory
{
	private static final String CONFIG_FILE_NAME = "DAOFactory.xml";
	private static DAOFactory instance;

	public synchronized static final DAOFactory getInstance() throws DAOException
	{
		//TODO logger
		if (instance == null)
		{
			try
			{
				final Digester digester = new Digester();
				digester.addObjectCreate("dao-factory", null, "factoryClass");
				digester
						.addSetProperty("dao-factory/property", "name", "value");
				instance = (DAOFactory) digester.parse(DAOFactory.class
						.getClassLoader().getResource(CONFIG_FILE_NAME)
						.toString());
			}
			catch (SAXException sax)
			{
				throw new DAOException(
						"Unable to parse configuration document.", sax);
			}
			catch (IOException io)
			{
				throw new DAOException(
						"Unable to parse configuration document.", io);
			}
		}
		return instance;
	}

	public abstract DAOInterface createDao() throws DAOException;
}
