/**
 * 
 */
package snippetlab.snippets.gui.mvc;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import snippetlab.snippets.AbstractSnippet;

import com.jgoodies.forms.layout.FormLayout;

/**
 * @author joao.viegas
 * snippetlab.snippets.gui.mvc.MVCSnippet
 */
public class MVCSnippet extends AbstractSnippet 
{
	MVCSession session = null;

	public void init()
	{
		super.init();
		
		//...set up an host panel example 
		JPanel hostpanel = new JPanel(new FormLayout("fill:pref:grow","fill:pref:grow"));
		panel.add(hostpanel,BorderLayout.CENTER);

		session = new MVCSession(hostpanel); 
		
	}
	
	@Override
	public void method() 
	{
		try
		{
			session.start();	
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
		
		
	}

}
