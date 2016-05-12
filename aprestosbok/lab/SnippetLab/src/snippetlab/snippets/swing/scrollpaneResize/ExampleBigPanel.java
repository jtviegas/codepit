/*
 * ExampleSmallPanel.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.scrollpaneResize;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 */
public class ExampleBigPanel extends JPanel
{

	private static final long serialVersionUID = 1L;

	public ExampleBigPanel()
	{
		super(new FormLayout
				(
				"10dlu,50dlu,5dlu,fill:100dlu:grow,5dlu,50dlu,10dlu",
				"10dlu,pref," +
				"5dlu,fill:50dlu:grow," +
				"5dlu,pref," +
				"5dlu,pref," +
				"10dlu"
				));
		CellConstraints cc = new CellConstraints();
		
		add(new JLabel("Just a label baby"), cc.xy(2, 2));
		add(new JTextField("Just a textField baby"), cc.xy(4, 2));
		add(new JTextField("Just a textField baby"), cc.xy(6, 2));
		
		add(new JLabel("Just another label baby"), cc.xy(2, 4));
		add(new JTextPane(), cc.xy(4, 4));
		add(new JTextField("Just a textField baby"), cc.xy(6, 4));
		
		add(new JLabel("Just another label baby"), cc.xy(2, 6));
		add(new JTextField("Just another textField baby"), cc.xy(4, 6));
		add(new JTextField("Just a textField baby"), cc.xy(6, 6));
		
		add(new JLabel("Just another label baby"), cc.xy(2, 8));
		add(new JTextField("Just another textField baby"), cc.xy(4, 8));
		add(new JTextField("Just a textField baby"), cc.xy(6, 8));
		
		
	}


}
