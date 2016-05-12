/*
 * AMediator.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.face;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.aprestos.code.bok.exceptions.InitializationException;
import org.aprestos.code.bok.gui.components.customdialog.YetAnotherCustomDialog;
import org.aprestos.code.bok.gui.components.customdialog.interfaces.DialogActionsInterface;
import org.aprestos.code.bok.gui.components.customdialog.misc.CustomDialogType;

import snippetlab.snippets.swing.face.model.Model;
import snippetlab.snippets.swing.face.view.ViewEngine;

/**
 * 
 */
public class GuiDataSession implements DialogActionsInterface
{
	private Model model;
	private boolean readwrite;
	private ViewEngine controller;
	private YetAnotherCustomDialog dialog;
	
	/**
	 * 
	 */
	public GuiDataSession(Model model, boolean readwrite)
	{
		this.model = model;
		this.readwrite = readwrite;
	}
	
	public void init()
	{
		controller = new ViewEngine(this.readwrite,(Model)this.model.clone());
		controller.init();
	}
	
	public void go() throws IOException,InitializationException
	{
		
		Properties props=new Properties();
		props.load(new FileInputStream("snippetlab.properties"));
		
		if(readwrite)
		{
			dialog = new YetAnotherCustomDialog(null,props,CustomDialogType._OK_APPLY_CANCEL,this);
		}
		else
		{
			dialog = new YetAnotherCustomDialog(null,props,CustomDialogType._OK_,this);
		}
		controller.load();
		dialog.setView(controller.getView());
		dialog.setVisibleToUser();
	}
	
	public void ok()
	{
		if(readwrite)
		{
			if(controller.queryChanges())
			{
				this.model = controller.getModel();
				System.out.println("new model in session");
			}	
		}
		dialog.setVisible(false);
	}
	
	public void apply()
	{
		if(readwrite)
		{
			if(controller.queryChanges())
			{
				this.model = controller.getModel();
				System.out.println("new model in session");
			}	
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
