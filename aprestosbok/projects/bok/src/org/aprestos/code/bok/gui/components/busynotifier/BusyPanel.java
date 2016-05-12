/*
 * BusyPanel.java
 */
/**
 * 
 */
package org.aprestos.code.bok.gui.components.busynotifier;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 */
public class BusyPanel extends JPanel
{

	private static final long serialVersionUID = 2897556430042684018L;
	
	private JLabel textLabel;
	
	public BusyPanel()
	{
		add(createMainPanel());

	}
	
	/**
	 * Allows changing of text being displayed.
	 * @param newText	- new text to display
	 */
	public void modifyText(String newText)
	{

		if((newText == null) || newText.isEmpty())
		{
			return;
		}
		textLabel.setText(newText);

	}
	
	private JPanel createMainPanel()
	{

		FormLayout layout;
		JPanel panel;
		CellConstraints cc;
		
		layout = new FormLayout("pref, 4dlu, 100dlu:grow",
				                "fill:10dlu");

		panel = new JPanel(layout);
		cc = new CellConstraints();
		
		textLabel = new JLabel();
		panel.add(textLabel, cc.xy(1, 1));
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setMinimumSize(new Dimension(20, 8));
		panel.add(progressBar, cc.xy(3,1));

		return panel;
	}

}
