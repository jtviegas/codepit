/*
 * TransactionScript.java Copyright (C) Wincor Nixdorf.
 */
package snippetlab.snippets.patterns.transactionscript;

import java.io.IOException;
import java.util.Properties;

import snippetlab.snippets.AbstractSnippet;

import com.wincor_nixdorf.commonpt.misc.MiscUtils;

public class TransactionScript extends AbstractSnippet
{

	@Override
	public void method()
	{
		try
		{
			Properties props = MiscUtils.getMyProperties(this.getClass());
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	
}
