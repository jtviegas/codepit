package org.tests.dumpread.data.impl;

import java.sql.PreparedStatement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tests.dumpread.data.interfaces.DBConnection;
import org.tests.dumpread.data.interfaces.RecordGW;
import org.tests.dumpread.data.model.Record;
import org.tests.dumpread.exceptions.GWException;

@Component("recordgw")
public class RecordGWImpl implements RecordGW 
{
	private static final Logger logger = LoggerFactory.getLogger(RecordGWImpl.class);
	
	@Autowired
	private DBConnection connection;
	private static final String SQL_INSERT = "insert into data(articleid,attribute,value,language,type) values(?,?,?,?,?)";
	private PreparedStatement insertStatement;


	public void init() throws GWException 
	{
		logger.debug("@init");
		try 
		{
			this.connection.open();
		} 
		catch (Exception e) 
		{
			throw new GWException(e);
		}	
		logger.debug("init@");
	}

	public void deinit() throws GWException
	{
		logger.debug("@deinit");
		try 
		{
			
			if(null != this.insertStatement)
				this.insertStatement.close();
			
			this.insertStatement = null;
				
			this.connection.close();
			
		} 
		catch (Exception e) 
		{
			throw new GWException(e);
		}
		logger.debug("deinit@");
	}




	public void startTransaction() throws GWException 
	{
		logger.debug("@startTransaction");
		
		try {
			this.connection.getConnection().setAutoCommit(false);
		} catch (Exception e) 
		{
			throw new GWException(e);
		}
		logger.debug("startTransaction@");
	}

	public void commitTransaction() throws GWException 
	{
		logger.debug("@commitTransaction");
		
		try {
			this.connection.getConnection().commit();
		} catch (Exception e) 
		{
			throw new GWException(e);
		}
		logger.debug("commitTransaction@");
	}

	public void rollbackTransaction() throws GWException 
	{
		logger.debug("@rollbackTransaction");
		
		try {
			this.connection.getConnection().rollback();
		} catch (Exception e) 
		{
			throw new GWException(e);
		}
		logger.debug("rollbackTransaction@");
	}

	public void insert(Record _r) throws GWException 
	{
		logger.debug("@insert");

		try 
		{
			if (null == this.insertStatement)
				this.insertStatement = this.connection.getConnection().prepareStatement(SQL_INSERT);
			
	
			this.insertStatement.setInt(1, _r.getArticleId());
			this.insertStatement.setString(2, _r.getAttribute());
			this.insertStatement.setString(3, _r.getValue());
			this.insertStatement.setShort(4, _r.getLanguage());
			this.insertStatement.setShort(5, _r.getType());
			
			this.insertStatement.execute();

		} 
		catch (Exception e) 
		{
			throw new GWException(e);
		}
		logger.debug("insert@");
	}

	public void insert(List<Record> _rs) throws GWException 
	{
		logger.debug("@insert(List)");
		
		try 
		{
			
			if (null == this.insertStatement)
				this.insertStatement = this.connection.getConnection().prepareStatement(SQL_INSERT);
			
			for (Record _r : _rs)
			{
				this.insertStatement.setInt(1, _r.getArticleId());
				this.insertStatement.setString(2, _r.getAttribute());
				this.insertStatement.setString(3, _r.getValue());
				this.insertStatement.setShort(4, _r.getLanguage());
				this.insertStatement.setShort(5, _r.getType());
				
				this.insertStatement.addBatch();
			}
			
			this.insertStatement.executeBatch();
		
		} 
		catch (Exception e) 
		{
			throw new GWException(e);
		}
		logger.debug("insert(List)@");
	}

}
