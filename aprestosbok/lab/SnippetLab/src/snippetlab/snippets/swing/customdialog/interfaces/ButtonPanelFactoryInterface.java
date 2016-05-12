/*
 * ButtonPanelFactoryInterface.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.customdialog.interfaces;

import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JPanel;

public interface ButtonPanelFactoryInterface
{
	JPanel getButtonPanel();

	void setProps(Properties props);

	void setDialog(JDialog dialog);
}
