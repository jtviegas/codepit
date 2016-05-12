/*
 * CommandStatusWrapper.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.misc.ChainProgressDialog.impl;

import org.apache.commons.chain.Command;

import snippetlab.snippets.misc.ChainProgressDialog.interfaces.CommandWrapperInterface;

public class CommandWrapperImpl implements CommandWrapperInterface
{
	private Command command;
	private int status;
	
	public CommandWrapperImpl(){}
	
	public CommandWrapperImpl(Command command)
	{
		this.command = command;
	}
	
	/**
	 * @see snippetlab.snippets.misc.ChainProgressDialog.interfaces.CommandWrapperInterface#getCommand()
	 */
	public Command getCommand()
	{
		return command;
	}
	
	/**
	 * @see snippetlab.snippets.misc.ChainProgressDialog.interfaces.CommandWrapperInterface#setCommand(org.apache.commons.chain.Command)
	 */
	public void setCommand(Command command)
	{
		this.command = command;
	}
	
	/**
	 * @see snippetlab.snippets.misc.ChainProgressDialog.interfaces.CommandWrapperInterface#getStatus()
	 */
	public int getStatus()
	{
		return status;
	}
	
	/**
	 * @see snippetlab.snippets.misc.ChainProgressDialog.interfaces.CommandWrapperInterface#setStatus(int)
	 */
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	public String toString()
	{
		String result=null;
		
		if(null != command)
			result=command.toString();
		
		return result;
	}
	
}
