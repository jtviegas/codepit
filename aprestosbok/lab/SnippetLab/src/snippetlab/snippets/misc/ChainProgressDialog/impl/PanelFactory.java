/*
 * PanelFactory.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.misc.ChainProgressDialog.impl;

import snippetlab.snippets.misc.ChainProgressDialog.interfaces.ChainStatusPanelInterface;
import snippetlab.snippets.misc.ChainProgressDialog.interfaces.PanelFactoryInterface;

public abstract class PanelFactory implements PanelFactoryInterface
{
	public static enum PanelType {SINGLE, PLURAL};
	
	public static PanelFactoryInterface getFactory(PanelType type)
	{
		PanelFactoryInterface result = null;
		
		switch(type)
		{
			case PLURAL:
				result = new PluralChainPanelFactory();
				break;
			case SINGLE:
				result=new SingleChainPanelFactory();
				break;
		}
		
		return result;
	}
	
	public abstract ChainStatusPanelInterface createPanel();

}
