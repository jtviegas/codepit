/*
 * AMediator.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.mmmodule;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.aprestos.code.bok.common.impl.BasicContextImpl;
import org.aprestos.code.bok.common.interfaces.BasicContextInterface;
import org.aprestos.code.bok.exceptions.InitializationException;
import org.aprestos.code.bok.gui.components.busynotifier.BusyNotifierFactory;
import org.aprestos.code.bok.gui.components.busynotifier.BusyNotifierInterface;
import org.aprestos.code.bok.gui.components.customdialog.YetAnotherCustomDialog;
import org.aprestos.code.bok.gui.components.customdialog.interfaces.DialogActionsInterface;
import org.aprestos.code.bok.gui.components.customdialog.misc.CustomDialogType;

import snippetlab.snippets.swing.mmmodule.ctrl.MMCtrl;
import snippetlab.snippets.swing.mmmodule.model.Model;

/**
 * 
 */
public class GuiDataSession implements DialogActionsInterface
{
	private Model model;
	private boolean readwrite;
	private MMCtrl controller;
	private YetAnotherCustomDialog dialog;
	
	
	/**
	 * 
	 */
	public GuiDataSession(Model model, boolean readwrite)
	{
		this.model = model;
		this.readwrite = readwrite;
	}

	
	public void go() throws IOException,InitializationException
	{
		final BusyNotifierInterface bn = BusyNotifierFactory.getBusyNotifier(BusyNotifierFactory.BusyNotifierType.SIMPLE_MODAL,
				null, null);
		bn.start("asa", "please wait", "please wait");
		
		SwingWorker<Void, Void> w = new SwingWorker<Void,Void>()
		{
			
			@Override
			protected Void doInBackground() throws Exception
			{
				BasicContextInterface c = new BasicContextImpl();
				c.put(Model.class.getName(), model);
				
				controller = new MMCtrl();
				controller.start(c);
				return null;
			}

			/**
			 * @see javax.swing.SwingWorker#done()
			 */
			@Override
			protected void done()
			{
				try
				{
					bn.stop();
					Properties props=new Properties();
					props.load(new FileInputStream("snippetlab.properties"));
					
					if(readwrite)
					{
						dialog = new YetAnotherCustomDialog(null,props,CustomDialogType._OK_APPLY_CANCEL,GuiDataSession.this);
						controller.constructReadWriteGUI(dialog.getViewPort());
					}
					else
					{
						dialog = new YetAnotherCustomDialog(null,props,CustomDialogType._OK_,GuiDataSession.this);
						controller.constructReadOnlyGUI(dialog.getViewPort());
					}
					
					controller.activate(new BasicContextImpl());
					dialog.setVisibleToUser();
				}
				catch(Exception x)
				{
					x.printStackTrace();
				}
				finally
				{
					if(bn.isBusy())
						bn.stop();
				}
				
				
			}

		};
		w.execute();
	}
	
	public void ok()
	{
		if(readwrite)
		{
			if(!controller.queryChanges())
				return;
			
			if(!controller.checkOperationRequisites(""))
				return;
			final BusyNotifierInterface bn = 
				BusyNotifierFactory.getBusyNotifier(
						BusyNotifierFactory.BusyNotifierType.SIMPLE_MODAL,
					 dialog, null);
			bn.start("asa", "please wait", "please wait");
			
			SwingWorker<Void, Void> w = new SwingWorker<Void,Void>()
			{

				@Override
				protected Void doInBackground() throws Exception
				{
					controller.insert();
					bn.stop();
					return null;
				}

				/**
				 * @see javax.swing.SwingWorker#done()
				 */
				@Override
				protected void done()
				{
					System.out.println("apply");

					if(bn.isBusy())
						bn.stop();
					
					dialog.setVisible(false);
				}
				
			};
			
			w.execute();
		}
		
	}
	
	public void apply()
	{
		if(readwrite)
		{
			if(!controller.queryChanges())
				return;
			
			if(!controller.checkOperationRequisites(""))
				return;
			
			final BusyNotifierInterface bn = 
				BusyNotifierFactory.getBusyNotifier(
						BusyNotifierFactory.BusyNotifierType.SIMPLE_MODAL,
						dialog, null);
			bn.start("asa", "please wait", "please wait");
			SwingWorker<Void, Void> w = new SwingWorker<Void,Void>()
			{

				@Override
				protected Void doInBackground() throws Exception
				{
					controller.update();
					bn.stop();
					return null;
				}

				/**
				 * @see javax.swing.SwingWorker#done()
				 */
				@Override
				protected void done()
				{
					
					if(bn.isBusy())
						bn.stop();
					
					controller.refresh();
					
				}
				
			};
			w.execute();
	
		}
		
	}
	
	public void cancel()
	{
		if(readwrite)
		{
			if(controller.queryChanges())
			{
				JOptionPane.showMessageDialog(dialog, "there where changes!!!but ok i'm leaving!");
			}	
		}
		
		dialog.setVisible(false);
	}

	public void help()
	{
		
	}
	
}
