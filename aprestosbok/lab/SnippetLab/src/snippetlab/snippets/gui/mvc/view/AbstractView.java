/**
 * 
 */
package snippetlab.snippets.gui.mvc.view;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

/**
 * @author joao.viegas
 * 
 */
public abstract class AbstractView extends JPanel
{
	private static final long serialVersionUID = 1L;
	protected ActionListener listener = null;
	protected String[] properties = null;

	/**
	 * Don´t forget to supply ActionEvents
	 */
	protected abstract void initComponents();
	
	public abstract Object getProperty(String name);
	
	public abstract void setProperty(String name, Object property);

	public AbstractView() 
	{
		initComponents();
	}
	
	public void setActionListener(ActionListener listener) 
	{
		this.listener = listener;
	}
	
}
