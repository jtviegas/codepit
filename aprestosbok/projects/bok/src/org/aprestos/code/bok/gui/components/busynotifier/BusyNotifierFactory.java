/*
 * BusyNotifierFactory.java
 */
/**
 * 
 */
package org.aprestos.code.bok.gui.components.busynotifier;

import java.awt.Window;

import javax.swing.JPanel;

/**
 * 
 */
public abstract class BusyNotifierFactory implements BusyNotifierInterface
{
	public static enum BusyNotifierType{SIMPLE_MODAL}
	
	public static BusyNotifierInterface getBusyNotifier(BusyNotifierType type, Window guiOwner, JPanel statusBar)
	{
		BusyNotifierInterface result = null;
		
		if(type.equals(BusyNotifierType.SIMPLE_MODAL))
			result = new SimpleModalBusyNotifier(guiOwner, statusBar);
		
		return result;
	}
	
	
	
	/*************************************************************
	USAGE EXAMPLE
	**************************************************************
	public void method()
	{
		JPanel statusbar = new JPanel(new FlowLayout());
		statusbar.add(new JLabel("AAAHH"));
		statusbar.setSize(100,6);
		panel.add(statusbar, BorderLayout.SOUTH);
		panel.validate();
		
		final BusyNotifierInterface b = BusyNotifierFactory.getBusyNotifier(
				BusyNotifierFactory.BusyNotifierType.SIMPLE_MODAL, frame, statusbar);
		
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
	
	*********************************************************************/

}
