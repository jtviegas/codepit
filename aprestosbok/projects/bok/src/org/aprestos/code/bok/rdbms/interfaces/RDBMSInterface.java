/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.aprestos.code.bok.rdbms.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

import org.aprestos.code.bok.rdbms.exceptions.RDBMSConnectionException;


/**
 *
 * @author jmv
 */
public interface RDBMSInterface 
{

    public Connection getConnection();

    public boolean isConnectionOpen();

    public void openConnection() throws RDBMSConnectionException;

    public void closeConnection() throws SQLException;
    
}
