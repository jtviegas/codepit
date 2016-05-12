/*
 * StatusPanelInterface.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.misc.ChainProgressDialog.interfaces;

import org.apache.commons.chain.Command;

/**
 * 
 */
public interface ChainStatusPanelInterface
{
	
	void update(Command command, int status);
	void setChain(Command[] chain);
	public void init();
}
