/**
 * 
 */
package snippetlab.snippets.gui.mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import snippetlab.snippets.gui.mvc.actions.impl.Close;
import snippetlab.snippets.gui.mvc.controller.BaseController;
import snippetlab.snippets.gui.mvc.exceptions.ClassContractException;
import snippetlab.snippets.gui.mvc.model.Model;
import snippetlab.snippets.gui.mvc.model.Movie;
import snippetlab.snippets.gui.mvc.view.AbstractView;
import snippetlab.snippets.gui.mvc.view.View;

import com.jgoodies.forms.layout.CellConstraints;

/**
 * @author joao.viegas
 *
 */
public class MVCSession implements ActionListener
{
	protected AbstractView view;
	protected Model<Movie> model; 
	protected BaseController controller;
	protected JPanel hostPanel;
	
	public MVCSession(JPanel hostPanel)
	{
		//...now we are going to simulate a model
		Movie movie = new Movie("my dear diary","nanni moretti",
				"drama",Arrays.asList("nanni moretti","valeria golino"));
		model = new Model<Movie>(movie);
		
		//...create the view
		this.view = new View();
				
		controller = new BaseController(new String[]{"name","director","genre","actors"});
		this.controller.setModel(model);
		this.controller.addView(view);
		
		this.hostPanel = hostPanel;
	}

	public void start() throws ClassContractException
	{
		
		this.controller.init();
		
		this.controller.addActionListener(this);
		
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run() 
			{
				hostPanel.add(view, (new CellConstraints()).xy(1, 1));
				hostPanel.validate();
				hostPanel.repaint();
			}
		});
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e instanceof Close)
		{
			
			
			
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run() 
				{
					hostPanel.remove(view);
					hostPanel.validate();
					hostPanel.repaint();
				}
			});
		}
		
	}
	
	public void stop()
	{
		this.controller.removeActionListener(this);
	}
}
