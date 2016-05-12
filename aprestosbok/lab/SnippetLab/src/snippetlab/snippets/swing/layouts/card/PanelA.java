/*
 * PanelA.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.layouts.card;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 */
public class PanelA extends JPanel
{
	private static final long serialVersionUID = 1L;

	public PanelA()
	{
		setLayout(new FormLayout("default","10dlu,default,10dlu,default,10dlu"));
		setBackground(Color.blue);
		CellConstraints cc = new CellConstraints();
		
		add(new JLabel("First label"), cc.xy(1, 2));
		add(new JLabel("Second label"), cc.xy(1, 4));
	}


}
