/*
 * ComboListenerDigressions.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.widgets.combo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import snippetlab.interfaces.SnippetInterface;

/**
 * 
 */
public class ComboListenerDigressions implements SnippetInterface
{
	//snippetlab.snippets.swing.widgets.combo.ComboListenerDigressions
	private JComboBox combo;
	private JButton ba;
	public ComboListenerDigressions()
	{
		combo = new JComboBox(new String[]{"a","b","c","d"});
		combo.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("action listened and combo selection is at " + combo.getSelectedItem());
				}
			
		});
		combo.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent e)
			{
				String action = "";
				
				switch(e.getStateChange())
				{
					case ItemEvent.SELECTED:
						action="selected";
						break;
					case ItemEvent.DESELECTED:
						action="deselected";
						break;
				}
				
				System.out.println("item state change listened and combo selection -> " + action + " " + combo.getSelectedItem());
			}
			
		});
		ba=new JButton("select a");
		ba.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				combo.setSelectedIndex(0);
			}
			
		});
		
	}

	/**
	 * @see snippetlab.interfaces.SnippetInterface#go(javax.swing.JPanel, javax.swing.JFrame)
	 */
	@Override
	public void go(JPanel panel, JFrame frame)
	{
		panel.add(combo, BorderLayout.CENTER);
		panel.add(ba, BorderLayout.SOUTH);
	}

}
