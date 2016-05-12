/*
 * CardHost.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.layouts.card;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class CardHost extends JPanel
{
	private static final long serialVersionUID = 1L;

	public CardHost()
	{
		final JPanel cardPanel = new JPanel(new CardLayout());
		cardPanel.add(new PanelA(), PanelA.class.getName());
		cardPanel.add(new PanelB(), PanelB.class.getName());
		
		setLayout(new FormLayout("default","pref,10dlu,default,pref"));
		final JComboBox cb = new JComboBox(new String[]{ PanelA.class.getName(), PanelB.class.getName()});
		cb.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				CardLayout cl = (CardLayout)(cardPanel.getLayout());
			    cl.show(cardPanel, (String)cb.getSelectedItem());
			    cardPanel.revalidate();
			    cardPanel.repaint();
			}
		});
		CellConstraints cc = new CellConstraints();
		
		add(cb,cc.xy(1,1));
		add(cardPanel, cc.xy(1, 3));
		add(new JLabel("Oh pra mim!!!"),cc.xy(1,4));
	}
	
	public static void main(String[] args)
	{
		JDialog d= new JDialog();
		d.setContentPane(new CardHost());
		d.pack();
		d.setVisible(true);
	}
	
	
	
}
