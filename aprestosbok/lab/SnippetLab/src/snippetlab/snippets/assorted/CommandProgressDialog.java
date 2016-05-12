/*
 * CommandProgressDialog.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.assorted;

import java.util.Random;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import snippetlab.snippets.AbstractSnippet;
import snippetlab.snippets.misc.ChainProgressDialog.ChainProgressDialog;


/**
 * 
 */
public class CommandProgressDialog extends AbstractSnippet
{

	/**
	 * @see snippetlab.snippets.AbstractSnippet#method()
	 */
	@Override
	public void method()
	{
		
		ChainProgressDialog d = new ChainProgressDialog(
				frame,new Command[]{new one(), new two(), new three(), new four()});
		d.setTitle("title");
		d.init();
		d.showToUser();
		

		//, new two(), new three(), new four()
	}

	
	private class one implements Command
	{

		@Override
		public boolean execute(Context arg0) throws Exception
		{
			Thread.sleep((new Random()).nextInt(6000));
			return true;
		}
		
	}
	
	private class two implements Command
	{

		@Override
		public boolean execute(Context arg0) throws Exception
		{
			Thread.sleep((new Random()).nextInt(6000));
			return true;
		}
		
	}
	
	private class three implements Command
	{

		@Override
		public boolean execute(Context arg0) throws Exception
		{
			Thread.sleep((new Random()).nextInt(6000));
			return true;
		}
		
	}
	
	private class four implements Command
	{

		@Override
		public boolean execute(Context arg0) throws Exception
		{
			Thread.sleep((new Random()).nextInt(6000));
			return true;
		}
		
	}	
		
}
