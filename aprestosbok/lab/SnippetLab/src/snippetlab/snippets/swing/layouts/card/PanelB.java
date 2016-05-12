/*
 * PanelB.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.layouts.card;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class PanelB extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelB()
	{
		
		setLayout(new FormLayout("default, 10dlu, default","10dlu,default,10dlu"));
		CellConstraints cc = new CellConstraints();
		add(new JLabel("Just a label:"),cc.xy(1, 2));
		add(new JTextField("Just a textField"),cc.xy(3, 2));
		setBackground(Color.yellow);
		
	}
}
