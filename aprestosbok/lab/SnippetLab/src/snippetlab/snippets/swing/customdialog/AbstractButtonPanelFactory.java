/*
 * AbstractButtonPanelFactory.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.customdialog;

import snippetlab.snippets.swing.customdialog.impl.CenteredOKButtonPanelFactory;
import snippetlab.snippets.swing.customdialog.interfaces.ButtonPanelFactoryInterface;
import snippetlab.snippets.swing.customdialog.misc.CustomDialogType;


public abstract class AbstractButtonPanelFactory implements ButtonPanelFactoryInterface
{

	public static ButtonPanelFactoryInterface getFactory(CustomDialogType type)
	{
		ButtonPanelFactoryInterface factory = null;
		
		switch(type)
		{
			case _OK_:
				factory = new CenteredOKButtonPanelFactory();
				break;
	
		}
		
		return factory;
	}
}
