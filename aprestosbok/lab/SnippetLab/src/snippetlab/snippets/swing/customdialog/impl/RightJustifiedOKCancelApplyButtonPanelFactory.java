/*
 * CenteredOKButtonPanelFactory.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.customdialog.impl;

import java.awt.Font;
import java.awt.Insets;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import snippetlab.snippets.swing.customdialog.AbstractButtonPanelFactory;
import snippetlab.snippets.swing.customdialog.misc.CustomDialogAction;
import snippetlab.snippets.swing.customdialog.misc.PropertyKey;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class RightJustifiedOKCancelApplyButtonPanelFactory extends AbstractButtonPanelFactory
{
	public Properties props;
	public JDialog dialog;
	
	public RightJustifiedOKCancelApplyButtonPanelFactory()
	{}

	
	
	public void setProps(Properties props)
	{
		this.props = props;
	}



	public void setDialog(JDialog dialog)
	{
		this.dialog = dialog;
	}



	@Override
	public JPanel getButtonPanel()
	{
		JPanel panel = new JPanel();
		
		JButton ok = new JButton(dialog.getRootPane().getActionMap().get(CustomDialogAction.OK));
		JButton cancel = new JButton(dialog.getRootPane().getActionMap().get(CustomDialogAction.CANCEL));
		JButton apply = new JButton(dialog.getRootPane().getActionMap().get(CustomDialogAction.APPLY));
		
		int okinset = new Integer(props.getProperty(PropertyKey.OK_INSET.getKey()));
		ok.setMargin(new Insets(okinset, okinset, okinset, okinset));
		int cancelinset = new Integer(props.getProperty(PropertyKey.CANCEL_INSET.getKey()));
		cancel.setMargin(new Insets(cancelinset, cancelinset, cancelinset, cancelinset));
		int applyinset = new Integer(props.getProperty(PropertyKey.APPLY_INSET.getKey()));
		apply.setMargin(new Insets(applyinset, applyinset, applyinset, applyinset));
		
		String buttonHeight = props.getProperty(PropertyKey.BUTTON_HEIGHT.getKey());
		String buttonWidth = props.getProperty(PropertyKey.BUTTON_WIDTH.getKey());

		dialog.getRootPane().setDefaultButton(ok);
		
		FormLayout buttonLayout = new FormLayout("fill:50dlu:grow, " + buttonWidth + "dlu, 20dlu," + buttonWidth + "dlu, 20dlu," + buttonWidth + "dlu, 20dlu"
				, 
				"fill:" + buttonHeight + "dlu");
		
		panel.setLayout(buttonLayout);
		CellConstraints cc = new CellConstraints();
		panel.add(ok, cc.xy(2, 1));
		panel.add(cancel, cc.xy(4, 1));
		panel.add(apply, cc.xy(6, 1));
		
		String name = props.getProperty(PropertyKey.BUTTON_FONT_NAME.getKey());
		int size = Integer.parseInt(props.getProperty(PropertyKey.BUTTON_FONT_SIZE.getKey()));
		int style = Integer.parseInt(props.getProperty(PropertyKey.BUTTON_FONT_STYLE.getKey()));
		
		ok.setFont(new Font(name, style, size));
		cancel.setFont(new Font(name, style, size));
		apply.setFont(new Font(name, style, size));

		return panel;
	}


}
