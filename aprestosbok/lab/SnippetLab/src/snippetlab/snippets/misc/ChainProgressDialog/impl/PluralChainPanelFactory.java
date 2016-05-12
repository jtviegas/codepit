/*
 * PluralPanelFactory.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.misc.ChainProgressDialog.impl;

import snippetlab.snippets.misc.ChainProgressDialog.interfaces.ChainStatusPanelInterface;

public class PluralChainPanelFactory extends PanelFactory
{

	@Override
	public ChainStatusPanelInterface createPanel()
	{
		return new PluralChainStatusPanel();
	}

}
