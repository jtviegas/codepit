/*
 * ButtonPanelFactoryInterface.java Copyright (C) EID, SA.
 */
package org.aprestos.code.bok.gui.components.customdialog.interfaces;

import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JPanel;

public interface ButtonPanelFactoryInterface
{
	JPanel getButtonPanel();

	void setProps(Properties props);

	void setDialog(JDialog dialog);
}
