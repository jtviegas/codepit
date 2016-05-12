/*
 * DataGateway.java Copyright (C) Wincor Nixdorf.
 */
package org.aprestos.code.patterns.transactionscript;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aprestos.code.misc.Money;
import org.aprestos.code.misc.SimpleDate;
import org.aprestos.code.rdbms.exceptions.RDBMSConnectionException;
import org.aprestos.code.rdbms.interfaces.RDBMSInterface;

public class DataGateway
{
	private static final String findRecognitionsSQL = "select amount from revenueRecognitions " +
			"where contract = ? and recognizedOn <= ?";
	private static final String findContractSQL = "select * from contracts c, products p " +
	"where c.id = ? and c.product = p.id";
	private static final String insertRecognitionSQL = "insert into revenueRecognitions" +
			"(contract,amount,recognizedOn)values(?,?,?)";
	
	private static final String insertProducts = "insert into products(id,name,type)values(?,?,?)";
	private static final String insertContracts = "insert into contracts(id,product,revenue,dateSigned) values(?,?,?,?)";
	
	private static final String deleteProducts = "delete from products";
	private static final String deleteContracts = "delete from contracts";
	private static final String deleteRecognitions = "delete from revenueRecognitions";
	
	private RDBMSInterface rdbms;
	
	
	
	public void setRDBMS(RDBMSInterface rdbms)
	{
		this.rdbms = rdbms;
	}
	
	public void insertRecognition(int contractID, Money amount, SimpleDate asof) throws SQLException, RDBMSConnectionException
	{
		if(!rdbms.isConnectionOpen())
			rdbms.openConnection();
		
		PreparedStatement ps = rdbms.getConnection().prepareStatement(insertRecognitionSQL);
		ps.setInt(1, contractID);
		ps.setDouble(2, amount.amount().doubleValue());
		ps.setDate(3, asof.toSQLDate());
		
		ps.executeUpdate();
		rdbms.closeConnection();
	}
	
	public ResultSet findContract(int contractID) throws SQLException, RDBMSConnectionException
	{
		ResultSet result = null;
		
		if(!rdbms.isConnectionOpen())
			rdbms.openConnection();
		
		PreparedStatement ps = rdbms.getConnection().prepareStatement(findContractSQL);
		ps.setInt(1, contractID);
		
		result = ps.executeQuery();

		return result;
	}
	
	public ResultSet findRecognitionsFor(int contractID, SimpleDate asof) throws SQLException, RDBMSConnectionException
	{
		ResultSet result = null;
		
		if(!rdbms.isConnectionOpen())
			rdbms.openConnection();
		
		PreparedStatement ps = rdbms.getConnection().prepareStatement(findRecognitionsSQL);
		ps.setInt(1, contractID);
		ps.setDate(2, asof.toSQLDate());
		result = ps.executeQuery();

		return result;
	}
	

	public void insertData() throws SQLException, RDBMSConnectionException
	{
		if(!rdbms.isConnectionOpen())
			rdbms.openConnection();
		
		//insert products
		PreparedStatement ps = rdbms.getConnection().prepareStatement(insertProducts);
		ps.setInt(1, 1);
		ps.setString(2, "Spreadsheet");
		ps.setString(3, "S");
		ps.execute();
		
		ps.setInt(1, 2);
		ps.setString(2, "WordProcessor");
		ps.setString(3, "W");
		ps.execute();
		
		ps.setInt(1, 3);
		ps.setString(2, "Database");
		ps.setString(3, "D");
		ps.execute();
		
		//insert contracts
		ps = rdbms.getConnection().prepareStatement(insertContracts);
		ps.setInt(1, 1);
		ps.setInt(2, 1);
		ps.setDouble(3, 2300.45);
		ps.setDate(4, (new SimpleDate(2009,2,1)).toSQLDate());
		ps.execute();
		
		ps.setInt(1, 2);
		ps.setInt(2, 2);
		ps.setDouble(3, 4300.23);
		ps.setDate(4, (new SimpleDate(2009,3,1)).toSQLDate());
		ps.execute();
		
		ps.setInt(1, 3);
		ps.setInt(2, 3);
		ps.setDouble(3, 908.1);
		ps.setDate(4, (new SimpleDate(2009,4,26)).toSQLDate());
		ps.execute();
		
		rdbms.closeConnection();
	}
	
	public void clearData() throws SQLException, RDBMSConnectionException
	{
		if(!rdbms.isConnectionOpen())
			rdbms.openConnection();
		
		//delete recognitions
		PreparedStatement ps = rdbms.getConnection().prepareStatement(deleteRecognitions);
		ps.execute();
		
		//delete contracts
		ps = rdbms.getConnection().prepareStatement(deleteContracts);
		ps.execute();
		
		//delete products
		ps = rdbms.getConnection().prepareStatement(deleteProducts);
		ps.execute();
		
		rdbms.closeConnection();
	}
}
