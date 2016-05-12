/*
 * CheckBoxListeners.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.widgets.checkbox;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import snippetlab.interfaces.SnippetInterface;

/**
 * 
 */
public class CheckBoxListeners implements SnippetInterface
{
	
	JCheckBox ck = new JCheckBox("click me!");
	JButton b = new JButton("click me!");
	
	public CheckBoxListeners()
	{
		ck.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				reportEvent("action performed listened");
			}
			
		});
		ck.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent e)
			{
				reportEvent("item state change listened with value " + e.getStateChange());
			}
			
		});
		
		b.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
			
				ck.setSelected(!ck.isSelected());
				
			}
			
		});
	}

	private void reportEvent(String event)
	{
		System.out.println("event " + event + " happened!");
	}
	/**
	 * @see snippetlab.interfaces.SnippetInterface#go(javax.swing.JPanel, javax.swing.JFrame)
	 */
	@Override
	public void go(JPanel panel, JFrame frame)
	{
		JPanel p=new JPanel(new FlowLayout());
		p.add(ck);
		p.add(b);
		
		panel.add(p, BorderLayout.CENTER);
	}

}
