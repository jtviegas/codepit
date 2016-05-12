/*
 * CenteredOKButtonPanelFactory.java
 */
package org.aprestos.code.bok.gui.components.customdialog.impl;

import java.awt.Font;
import java.awt.Insets;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.aprestos.code.bok.gui.components.customdialog.factory.AbstractButtonPanelFactory;
import org.aprestos.code.bok.gui.components.customdialog.misc.CustomDialogAction;
import org.aprestos.code.bok.gui.components.customdialog.misc.PropertyKey;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class CenteredOKButtonPanelFactory extends AbstractButtonPanelFactory
{
	public Properties props;
	public JDialog dialog;
	
	public CenteredOKButtonPanelFactory()
	{}

	
	
	public void setProps(Properties props)
	{
		this.props = props;
	}



	public void setDialog(JDialog dialog)
	{
		this.dialog = dialog;
	}


	public JPanel getButtonPanel()
	{
		JPanel panel = new JPanel();
		
		JButton ok = new JButton(dialog.getRootPane().getActionMap().get(CustomDialogAction.OK));
		
		int inset = new Integer(props.getProperty(PropertyKey.OK_INSET.getKey()));
		ok.setMargin(new Insets(inset, inset, inset, inset));

		String buttonHeight = props.getProperty(PropertyKey.BUTTON_HEIGHT.getKey());
		String buttonWidth = props.getProperty(PropertyKey.BUTTON_WIDTH.getKey());

		dialog.getRootPane().setDefaultButton(ok);
		
		FormLayout buttonLayout = new FormLayout("fill:50dlu:grow, " + buttonWidth + "dlu, fill:50dlu:grow"
				, 
				"fill:" + buttonHeight + "dlu");
		
		panel.setLayout(buttonLayout);
		CellConstraints cc = new CellConstraints();
		panel.add(ok, cc.xy(2, 1));

		String name = props.getProperty(PropertyKey.BUTTON_FONT_NAME.getKey());
		int size = Integer.parseInt(props.getProperty(PropertyKey.BUTTON_FONT_SIZE.getKey()));
		int style = Integer.parseInt(props.getProperty(PropertyKey.BUTTON_FONT_STYLE.getKey()));
		
		ok.setFont(new Font(name, style, size));

		return panel;
	}


}
