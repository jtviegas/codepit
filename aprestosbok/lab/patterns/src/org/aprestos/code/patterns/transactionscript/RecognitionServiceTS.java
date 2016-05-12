/*
 * RecognitionService.java Copyright (C) Wincor Nixdorf.
 */
package org.aprestos.code.patterns.transactionscript;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.aprestos.code.exceptions.WrongArgumentException;
import org.aprestos.code.misc.Money;
import org.aprestos.code.misc.SimpleDate;
import org.aprestos.code.rdbms.exceptions.RDBMSConnectionException;

public class RecognitionServiceTS
{
	private DataGateway gw;

	/**
	 * @param gw the gw to set
	 */
	public void setGw(DataGateway gw)
	{
		this.gw = gw;
	}

	public void calculateRevenueRecognitions(int contractNumber) throws SQLException, RDBMSConnectionException
	{
		ResultSet contracts = gw.findContract(contractNumber);
		contracts.next();
		Money totalRevenue = Money.euros(contracts.getDouble("revenue"));
		SimpleDate recognitionDate = new SimpleDate(contracts.getDate("dateSigned"));
		String type = contracts.getString("type");
		
		if(type.equals("S"))
		{
			Money[] allocation = totalRevenue.allocate(3);
			gw.insertRecognition(contractNumber, allocation[0], recognitionDate);
			gw.insertRecognition(contractNumber, allocation[1], recognitionDate.addDays(60));
			gw.insertRecognition(contractNumber, allocation[2], recognitionDate.addDays(90));
		}
		else if(type.equals("W"))
		{
			gw.insertRecognition(contractNumber, totalRevenue, recognitionDate);
		}
		else if(type.equals("D"))
		{
			Money[] allocation = totalRevenue.allocate(3);
			gw.insertRecognition(contractNumber, allocation[0], recognitionDate);
			gw.insertRecognition(contractNumber, allocation[1], recognitionDate.addDays(30));
			gw.insertRecognition(contractNumber, allocation[2], recognitionDate.addDays(60));
		}
	}
	
	public Money recognizeRevenue(int contractNumber, SimpleDate asof) throws SQLException, RDBMSConnectionException, WrongArgumentException
	{
		Money result = Money.euros(0);
		ResultSet rs = gw.findRecognitionsFor(contractNumber, asof);
		while(rs.next())
		{
			result = result.add(Money.euros(rs.getDouble(1)));
		}
		
		return result;
	}
}
