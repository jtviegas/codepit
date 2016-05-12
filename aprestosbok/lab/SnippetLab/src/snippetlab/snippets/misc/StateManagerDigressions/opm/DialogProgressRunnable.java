/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.StateManagerDigressions.opm;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author jtviegas
 */
public class DialogProgressRunnable implements Runnable
{

	private final OperationsProgressManager progManager;
	private final String operationKey;
	private final String operationText;

	
	
	public DialogProgressRunnable(String opKey,
			                      String opText,
			                      OperationsProgressManager mngr)
	{
		System.out.println("constructor");
		operationKey = opKey;
		operationText = opText;
		progManager = mngr;

		System.out.println("constructor");
	}

	@Override
	public void run()
	{
		Thread.currentThread().setName("OP-DIALOG-PROGRESS-THREAD");

		System.out.println("run");		
		final JFrame frame = new JFrame();
		
		// if operation is done, do nothing
		if(progManager.isRunning(operationKey))
		{
			// add progress bar to status bar
			System.out.println("run: going to show dialog progress bar");
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					buildBusyFrame(frame);
					frame.setVisible(true);
				}
			});
			System.out.println("run: progress dialog shown; wait for operation to be done");

			// wait until the operation is done
			while(progManager.isRunning(operationKey))
			{
				try{Thread.sleep(500);}catch(Exception e){}
			}
			
			// cancel dialog progress
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					frame.dispose();
				}
			});
			
			System.out.println("run: done");			
		}
		else
		{
			System.out.println("run: operation " + operationKey + " was done before showing progress bar");			
		}		
	}
	
	private void buildBusyFrame(JFrame frame)
	{
		frame.setTitle("estou esperando...");
		frame.setLayout(new FlowLayout());
		
		JLabel busyLabel = new JLabel("X");
		frame.add(busyLabel);

		
		JLabel textLabel = new JLabel(operationText);
		frame.add(textLabel);
		
		JButton cancelButton=new JButton("cancel");
		
		cancelButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				progManager.endOperation(operationKey);
			}
			
		});
		
		frame.add(cancelButton);
		
		frame.setSize(300, 80);
		frame.setResizable(false);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width / 2) - (frame.getWidth() / 2),
				          (screenSize.height / 2) - (frame.getHeight() / 2));
	}
}

