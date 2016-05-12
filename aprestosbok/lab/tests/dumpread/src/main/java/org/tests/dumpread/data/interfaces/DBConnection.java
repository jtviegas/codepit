package org.tests.dumpread.data.interfaces;

import java.sql.Connection;

import org.tests.dumpread.exceptions.DBConnectionException;

public interface DBConnection {

	/**
	 * @return the connection
	 */
	public Connection getConnection();

	public void open() throws DBConnectionException;
	public void close() throws DBConnectionException;

}