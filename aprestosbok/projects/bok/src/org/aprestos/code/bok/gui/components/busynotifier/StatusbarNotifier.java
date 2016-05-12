/*
 * StatusbarNotifier.java
 */
/**
 * 
 */
package org.aprestos.code.bok.gui.components.busynotifier;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * 
 */
public class StatusbarNotifier implements Runnable
{
	private final JPanel statusbar;
	private String text;
	private BusyNotifierInterface notifier;
	
	public StatusbarNotifier(BusyNotifierInterface notifier, String text, JPanel statusbar)
	{
		this.text = text;
		this.statusbar = statusbar;
		this.notifier = notifier;
	}

	@Override
	public void run()
	{
		Thread.currentThread().setName("BUSYNOTIFIER-STATUSBAR-THREAD");
	
		final BusyPanel busyPanel = new BusyPanel();
		
		// if operation is done, do nothing

			// add progress bar to status bar
			SwingUtilities.invokeLater(new Runnable(){
				public void run()
				{					
					busyPanel.modifyText(text);
					statusbar.add(busyPanel);
					statusbar.validate();
					statusbar.repaint();
				} 
			});


			// wait until the operation is done
			while(notifier.isBusy())
			{
				try{Thread.sleep(500);}catch(Exception e){}
			}
			
			// remove progress bar from status bar
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){					
					statusbar.remove(busyPanel);
					statusbar.validate();
					statusbar.repaint();

				}
			});
			


	}

}
