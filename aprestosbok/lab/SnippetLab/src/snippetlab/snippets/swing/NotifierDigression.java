/*
 * NotifierDigression.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import snippetlab.snippets.AbstractSnippet;
import snippetlab.snippets.swing.BusyNotifier.BusyNotifierFactory;
import snippetlab.snippets.swing.BusyNotifier.BusyNotifierInterface;

/**
 * 
 */
public class NotifierDigression extends AbstractSnippet
{


	@Override
	public void method()
	{
		JPanel statusbar = new JPanel(new FlowLayout());
		statusbar.add(new JLabel("AAAHH"));
		statusbar.setSize(100,6);
		panel.add(statusbar, BorderLayout.SOUTH);
		panel.validate();
		
		final BusyNotifierInterface b = BusyNotifierFactory.getBusyNotifier(
				BusyNotifierFactory.BusyNotifierType.SIMPLE_MODAL, frame, statusbar);
		
		b.start("bar", "dialog", "title");
		
		SwingWorker<Void, Void> o = new SwingWorker<Void, Void>()
		{

			@Override
			protected Void doInBackground() throws Exception
			{
				
				try
				{
					Thread.sleep(8000);
				}
				catch(Exception x)
				{
					
				}
				
				return null;
			}

			@Override
			protected void done()
			{
				b.stop();
			}
			
			
			
		};
		
		o.execute();
	}

}
