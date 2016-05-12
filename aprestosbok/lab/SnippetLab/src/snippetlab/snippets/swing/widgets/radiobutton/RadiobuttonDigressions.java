/*
 * RadiobuttonDigressions.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.widgets.radiobutton;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import snippetlab.interfaces.SnippetInterface;

public class RadiobuttonDigressions implements SnippetInterface
{
	//snippetlab.snippets.swing.widgets.radiobutton.RadiobuttonDigressions
	private JRadioButton rb1 = new JRadioButton("1"),rb2 = new JRadioButton("2");
	private JButton b = new JButton("click first radio!");
	private JButton b2 = new JButton("select first radio!");
	
	public RadiobuttonDigressions()
	{
		
	}
	
	private void init()
	{
		rb1.addActionListener(new JRadioListener());
		rb1.addItemListener(new JRadioListener());

		
		b.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				rb1.doClick();
				
			}
			
		});
		
		b2.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				rb1.setSelected(true);
				
			}
			
		});

		rb1.setEnabled(false);
		//rb1.doClick();
		rb1.setSelected(true);
		rb1.setEnabled(true);
	}

	/**
	 * @see snippetlab.interfaces.SnippetInterface#go(javax.swing.JPanel, javax.swing.JFrame)
	 */
	@Override
	public void go(JPanel panel, JFrame frame)
	{
		init();
		JPanel p=new JPanel(new FlowLayout());
		ButtonGroup bg = new ButtonGroup();
		bg.add(rb1);
		bg.add(rb2);
		p.add(rb1);
		p.add(rb2);
		p.add(b);
		p.add(b2);

		
		panel.add(p, BorderLayout.CENTER);
	}
	
	private class JRadioListener implements ItemListener, ActionListener
	{

		
		
		@Override
		public void itemStateChanged(ItemEvent e)
		{
			if(e.getStateChange() == ItemEvent.SELECTED)
			{
				System.out.println("ItemEvent ItemEvent.SELECTED happened!");
			}
			else if(e.getStateChange() == ItemEvent.DESELECTED)
			{
				System.out.println("ItemEvent ItemEvent.DESELECTED happened!");
			}
			else
				System.out.println("ItemEvent " + e.getStateChange() + " happened!");
			
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
				System.out.println("ActionEvent  happened!");

		}
		
		
	}
	
}
