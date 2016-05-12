/*
 * JOptionMessagesDigression.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing;

import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import snippetlab.snippets.AbstractSnippet;

/**
 * 
 */
public class JOptionMessagesDigression extends AbstractSnippet
{

	/**
	 * 
	 */
	public JOptionMessagesDigression()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see snippetlab.snippets.AbstractSnippet#method()
	 */
	@Override
	public void method()
	{
		JDialog d = getDialog(frame);
		d.setVisible(true);

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
				JOptionPane.showMessageDialog(b, "i'm owned by my dialog window");
			}
		});
		
		p.add(b3);
		
		d.getContentPane().add(p);
		
		return d;
	}

}
