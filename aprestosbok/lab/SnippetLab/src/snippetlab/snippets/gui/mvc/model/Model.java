/**
 * 
 */
package snippetlab.snippets.gui.mvc.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;


/**
 * GenericModel
 * 
 * @author joao.viegas
 *
 */
public class Model<T>  
{
	
	protected PropertyChangeSupport pcs = null;
	
	protected T model;
	
	public Model(T model)
	{
	    this.model = model;
	    pcs = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
	    pcs.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
	    pcs.removePropertyChangeListener(listener);
	}

	
	public void setProperty(String property, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
	{
	    Method getmethod = model.getClass().getMethod("get" + StringUtils.capitalize(property));
	    Object oldvalue = getmethod.invoke(model);
	    
	    Method setmethod = model.getClass().getMethod("set" + StringUtils.capitalize(property), new Class[]{value.getClass()});
	    setmethod.invoke(model, value);
	    
	    pcs.firePropertyChange(property, oldvalue, value);
	}

	public Object getProperty(String property) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
	{
		Object result = null;
		Method getmethod = model.getClass().getMethod("get" + StringUtils.capitalize(property));
	    result = getmethod.invoke(model);
	    return result;
	}
	
}
