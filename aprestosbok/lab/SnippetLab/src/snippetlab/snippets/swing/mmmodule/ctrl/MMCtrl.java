/*
 * MMCtrl.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.mmmodule.ctrl;

import javax.swing.JOptionPane;
import javax.swing.JViewport;

import org.aprestos.code.bok.common.interfaces.BasicContextInterface;

import snippetlab.snippets.swing.mmmodule.model.Model;
import snippetlab.snippets.swing.mmmodule.view.ViewEngine;

/**
 * 
 */
public class MMCtrl
{

	private static final String DELETE_OPERATION="delete";
	private static final String INSERT_OPERATION="insert";
	private static final String UPDATE_OPERATION="update";
	
	private BasicContextInterface controllerContext;
	private BasicContextInterface activationContext;
	private ViewEngine view;
	private JViewport parent;
	
	public void start(BasicContextInterface controllerContext) 
	{
		this.controllerContext = controllerContext;
		try
		{
			Thread.sleep(3000);	
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
	}
	
	/**
	 * expected to be run on GUI
	 * @param parent
	 */
	public void constructReadOnlyGUI(JViewport parent) 
	{
		Model model = (Model)this.controllerContext.get(Model.class.getName());
		view = new ViewEngine(false, model);
		view.init();
		this.parent = parent;
	}
	
	/**
	 * expected to be run on GUI
	 * @param parent
	 */
	public void constructReadWriteGUI(JViewport parent)
	{
		Model model = (Model)this.controllerContext.get(Model.class.getName());
		view = new ViewEngine(true, model);
		view.init();
		this.parent = parent;
	}
	
	/**
	 * expected to be run on GUI 
	 * @param activationContext
	 */
	public void activate(BasicContextInterface activationContext)
	{
		view.load();
		parent.setView(view.getView());
	}
	
	public void deactivate() 
	{
		parent.setView(null);
	}
	
	public void setControllerContext(BasicContextInterface controllerContext) 
	{
		this.controllerContext = controllerContext;
	}

	public BasicContextInterface getControllerContext() 
	{
		return this.controllerContext;
	}

	public void setActivationContext(BasicContextInterface activationContext)
	{
		this.activationContext = activationContext;
	}

	public BasicContextInterface getActivationContext()
	{
		return this.activationContext;
	}
	
	public void stop() 
	{
		view = null;
	}
	
	/**
	 * expected to run on gui
	 * @param operation
	 * @return
	 */
	public boolean checkOperationRequisites(String operation)
	{
		boolean result = false;
		
		if(JOptionPane.showConfirmDialog(null, "yes") == 0)
			result=true;
		
		return result;
	}
	
	public void insert()
	{
		persistMethod();
	}

	public void update() 
	{
		persistMethod();
	}

	public void delete()
	{
		unpersistMethod();
	}

	public boolean queryChanges()
	{
		return view.queryChanges();
	}

	
	private void persistMethod()
	{
		try
		{
			Thread.sleep(7000);	
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
		
		Model model = view.getModel();
		this.controllerContext.put(Model.class.getName(), model);
		
	}
	
	private void unpersistMethod()
	{
		try
		{
			Thread.sleep(7000);	
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
		
		Model model = (Model)this.controllerContext.get(Model.class.getName());
		
	}

	/**
	 * to run on edt
	 */
	public void refresh()
	{
		Model model = (Model)this.controllerContext.get(Model.class.getName());
		view.unload();
		view.setModel(model);
		view.load();
	}

	


}
