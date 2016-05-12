/*
 * FlashableActionDigression.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.assorted;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;

import snippetlab.snippets.AbstractSnippet;

public class FlashableActionDigression extends AbstractSnippet
{

	
	@Override
	public void method()
	{
		JButton b= new JButton(new FlasherAction());
		b.setPreferredSize(new Dimension(100,100));
		
		this.panel.add(b, BorderLayout.CENTER);

	}

	class FlasherAction extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Timer timer;
		private Icon[] icons = new Icon[]{new ImageIcon("png/moz.png"),new ImageIcon("png/doc.png")};
		
		public FlasherAction()
		{
//			putValue("iconA", new ImageIcon("png/moz.png"));
//			putValue("iconB", new ImageIcon("png/doc.png"));
			
			putValue(AbstractAction.NAME, "flash button");
			
			timer = new Timer(3000, new ActionListener()
			{
				int index=0;
				@Override
				public void actionPerformed(ActionEvent e)
				{
					putValue(Action.SMALL_ICON, icons[index]);
					
					if(++index == icons.length)
					{	
						index=0;
					}
				}
				
			});
			
			loadPrefs();
			addPropertyChangeListener(new AListener());
		}
		
		private void loadPrefs()
		{
			Preferences p = Preferences.systemNodeForPackage(this.getClass());
			if(p.getBoolean("flashing", true))
				timer.start();
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(timer.isRunning())
				timer.stop();
			else
				timer.restart();
		}

		@Override
		protected void finalize() throws Throwable
		{
			Preferences p = Preferences.systemNodeForPackage(this.getClass());
			if(timer.isRunning())
			{
				timer.stop();
				p.putBoolean("flashing", true);
			}
			else
			{
				p.putBoolean("flashing", false);
			}
			
			super.finalize();
		}
		
		class AListener implements PropertyChangeListener
		{

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				System.out.println(evt.toString());
				
			}
			
		}
		
	}
	
}
