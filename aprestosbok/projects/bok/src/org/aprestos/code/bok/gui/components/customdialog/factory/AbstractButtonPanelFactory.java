/*
 * AbstractButtonPanelFactory.java Copyright (C) EID, SA.
 */
package org.aprestos.code.bok.gui.components.customdialog.factory;

import org.aprestos.code.bok.gui.components.customdialog.impl.CenteredOKButtonPanelFactory;
import org.aprestos.code.bok.gui.components.customdialog.impl.RightJustifiedOKCancelApplyButtonPanelFactory;
import org.aprestos.code.bok.gui.components.customdialog.interfaces.ButtonPanelFactoryInterface;
import org.aprestos.code.bok.gui.components.customdialog.misc.CustomDialogType;


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
	
			case _OK_APPLY_CANCEL:
				factory = new RightJustifiedOKCancelApplyButtonPanelFactory();
		}
		
		return factory;
	}
}
