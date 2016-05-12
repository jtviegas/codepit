/*
 * DialogNotifier.java
 */
/**
 * 
 */
package org.aprestos.code.bok.gui.components.busynotifier;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Dialog.ModalityType;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXBusyLabel;

/**
 * 
 */
public class DialogNotifier implements Runnable
{
	private final BusyNotifierInterface notifier;
	private final String text;
	private final String title;
	private Window guiOwner;
	
	public DialogNotifier(BusyNotifierInterface notifier, Window guiOwner, String text, String title)
	{
		this.text = text;
		this.title = title;
		this.notifier = notifier;
		this.guiOwner = guiOwner;
	}

	@Override
	public void run()
	{
		Thread.currentThread().setName("BUSYNOTIFIER-DIALOG-THREAD");

	
		final JDialog frame = new JDialog(this.guiOwner, ModalityType.APPLICATION_MODAL);
		
		// if operation is done, do nothing
		if(notifier.isBusy())
		{
			SwingUtilities.invokeLater(new Runnable(){
				public void run()
				{
					buildBusyFrame(frame);
					frame.setVisible(true);
				}
			});
			
			// wait until the operation is done
			while(notifier.isBusy())
			{
				try{Thread.sleep(500);}catch(Exception e){}
			}
			
			// cancel dialog progress
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					frame.dispose();
				}
			});
			
		
		}
	}
	
	private void buildBusyFrame(JDialog frame)
	{
		frame.setTitle(title);
		frame.setLayout(new FlowLayout());
		
		JXBusyLabel busyLabel = new JXBusyLabel(new Dimension(30,30));
		frame.add(busyLabel);
		busyLabel.setBusy(true);
		
		JLabel textLabel = new JLabel(text, JLabel.CENTER);
		frame.add(textLabel);
		
		frame.setSize(200, 80);
		frame.setResizable(false);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if(null != this.guiOwner)
		{
			frame.setLocationRelativeTo(this.guiOwner);
		}
		else
		{
			frame.setLocation((screenSize.width / 2) - (frame.getWidth() / 2),
			          (screenSize.height / 2) - (frame.getHeight() / 2));	
		}
		

	}

}
