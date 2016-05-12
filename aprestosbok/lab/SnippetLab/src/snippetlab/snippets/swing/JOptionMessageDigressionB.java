/*
 * JOptionMessageDigressionB.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import snippetlab.interfaces.SnippetInterface;

/**
 * 
 */
public class JOptionMessageDigressionB implements SnippetInterface
{

	private JButton a,b;
	
	/**
	 * 
	 */
	public JOptionMessageDigressionB()
	{
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * @see snippetlab.interfaces.SnippetInterface#go(javax.swing.JPanel, javax.swing.JFrame)
	 */
	@Override
	public void go(JPanel panel,final JFrame frame)
	{
		a= new JButton("a");
		panel.add(a, BorderLayout.WEST);
		
		b = new JButton("b");
		panel.add(b, BorderLayout.EAST);
		b.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				getDialog(frame).setVisible(true);
			}
		});
	}

	private JDialog getDialog(Window owner)
	{
		final JDialog d = new JDialog(owner);
		JPanel p = new JPanel(new FlowLayout());
		final JButton b=new JButton("get message dialog");
		b.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(d.getOwner(), "i'm owned by the dialog owner");
			}
		});
		
		p.add(b);
		
		JButton b2=new JButton("get another message dialog");
		b2.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(d, "i'm owned by my dialog window");
			}
		});
		
		p.add(b2);
		
		JButton b3=new JButton("get yet another message dialog");
		b3.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(a, "i'm owned by my dialog window");
			}
		});
		
		p.add(b3);
		
		d.getContentPane().add(p);
		
		return d;
	}
	
}
