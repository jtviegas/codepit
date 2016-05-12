/**
 * 
 */
package snippetlab.snippets.gui.mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.ExceptionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import snippetlab.snippets.gui.mvc.actions.impl.Close;
import snippetlab.snippets.gui.mvc.actions.impl.Delete;
import snippetlab.snippets.gui.mvc.actions.impl.Save;
import snippetlab.snippets.gui.mvc.exceptions.ClassContractException;
import snippetlab.snippets.gui.mvc.model.Model;
import snippetlab.snippets.gui.mvc.view.AbstractView;

/**
 * @author joao.viegas
 * 
 * 
 * Works just like a mediator between the view and the model and
 * lets everybody know when it is over with its work
 */
public class  BaseController implements PropertyChangeListener,
		ActionListener 
{
	//views can be multiple, the one responsible for the editing will
	//notify us about it
	protected List<AbstractView> views = new ArrayList<AbstractView>();
	//there will be interaction with one model
	protected Model<?> model = null;
	//listeners are allowed, they will be notified of closing events
	protected List<ActionListener> listeners = new ArrayList<ActionListener>();
	//exception listeners
	protected List<ExceptionListener> exceptionListeners = new ArrayList<ExceptionListener>();
	//properties to be handled
	
	String[] properties = null;
	
	public BaseController(){}
	
	public BaseController(String[] properties)
	{
		this.properties = properties;
	}
	
	public void setProperties(String[] properties)
	{
		this.properties = properties;
	}
	
	public void setModel(Model<?> model) 
	{
		if(null != model)
			model.addPropertyChangeListener(this);
		else
			this.model.removePropertyChangeListener(this);

		this.model = model;
	}

	public void addView(AbstractView view) 
	{
		views.add(view);
		view.setActionListener(this);
	}

	public void removeView(AbstractView view) 
	{
		views.remove(view);
		view.setActionListener(null);
	}

	public void addActionListener(ActionListener listener) 
	{
		listeners.add(listener);
	}

	public void removeActionListener(ActionListener listener) 
	{
		listeners.remove(listener);
	}

	public void addExceptionListener(ExceptionListener listener) 
	{
		exceptionListeners.add(listener);
	}

	public void removeExceptionListener(ExceptionListener listener) 
	{
		exceptionListeners.remove(listener);
	}

	protected void broadcastException(Exception x)
	{
		for(ExceptionListener el:exceptionListeners)
			el.exceptionThrown(x);
		
		x.printStackTrace();
	}
	
	/**
	 * Notifications from property changes 
	 * are sent by the model, we must update the view
	 * and we are going to do it in the Swing thread
	 */
	@Override
	public void propertyChange(PropertyChangeEvent arg0) 
	{
	    final Object newproperty = arg0.getNewValue();
	    final String property = arg0.getPropertyName();
	    
	    if(null != newproperty)
	    {
	    	SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run() 
				{
					for(AbstractView view:views)
					{
						if(!newproperty.equals(view.getProperty(property)))
							view.setProperty(property, newproperty);	
					}
				}
			});
	    }
	    else
	    {
	    	SwingUtilities.invokeLater(new Runnable()
			{
	    		@Override
				public void run() 
				{
	    			for(AbstractView view:views)
					{
						view.setProperty(property, newproperty);	
					}
				}
			});	
	    }
	    
	}
	
	/**
	 * Notifications sent by the view must be handled
	 * by each specific controller, for each specific
	 * action
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e instanceof Close)
		{
			close();
			
			//notify listeners about closing time
			for(ActionListener listener:listeners)
				listener.actionPerformed(e);
			
		}
		
		if(e instanceof Delete)
			delete();
			
		if(e instanceof Save)
			save(e);
	}
	

	public void init() throws ClassContractException
	{
		for(AbstractView view:views)
		{
			try
			{
				
				for(String p : properties)
					view.setProperty(p, model.getProperty(p));
			}
			catch(Exception x)
			{
				throw new ClassContractException("@" + this.getClass().getName() + ".load()",x);
			}
		}
	}
	
	protected void close() 
	{
		//do nothing
	}

	protected void delete() 
	{
		this.model = null;
	}

	/**
	 * update model with values from the view
	 */
	protected void save(ActionEvent e)
	{
		try
		{
			if(e.getSource() instanceof AbstractView)
			{
				AbstractView view = (AbstractView) e.getSource();

				for(String p : properties)
					model.setProperty(p, view.getProperty(p));
			}	
		}
		catch(Exception x)
		{
			broadcastException(new ClassContractException("@" + this.getClass().getName() + ".save(ActionEvent e)",x));
		}
		
		
	}
	
	

}
