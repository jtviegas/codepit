/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.aprestos.code.rdbms.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

import org.aprestos.code.rdbms.exceptions.RDBMSConnectionException;



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
