/*
 * SinglePanelFactory.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.misc.ChainProgressDialog.impl;

import snippetlab.snippets.misc.ChainProgressDialog.interfaces.ChainStatusPanelInterface;

public class SingleChainPanelFactory extends PanelFactory
{

	@Override
	public ChainStatusPanelInterface createPanel()
	{
		return new SingleChainStatusPanel();
	}

}
