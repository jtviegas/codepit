/*
 * SimpleHost.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.layouts.card;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class SimpleHost extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JPanel panelA,panelB;
	
	public SimpleHost()
	{
		panelA = new PanelA();
		panelB = new PanelB();
		final CellConstraints cc = new CellConstraints();
		
		setLayout(new FormLayout("default","pref,10dlu,pref,pref"));
		final JComboBox cb = new JComboBox(new String[]{ PanelA.class.getName(), PanelB.class.getName()});
		cb.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(cb.getSelectedItem().equals(PanelA.class.getName()))
				{
					//remove(panelB);

					add(panelB, cc.xy(1, 3));
					validate();
					repaint();
					
				}
				else
				{
					remove(panelB);
					
//					add(panelB, cc.xy(1, 3));
					validate();
					repaint();

				}
				
			}
		});
		
		
		add(cb,cc.xy(1,1));
		add(new JLabel("Oh pra mim!!!"),cc.xy(1,4));
	}
	
	public static void main(String[] args)
	{
		JDialog d= new JDialog();
		d.setContentPane(new SimpleHost());
		d.pack();
		d.setVisible(true);
	}
	
	
	
}
