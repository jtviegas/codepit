/*
 * JXErrorPaneDigressions.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing;




import java.util.logging.Level;

import javax.swing.UIManager;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

import snippetlab.snippets.AbstractSnippet;

/**
 * 
 */
public class JXErrorPaneDigressions extends AbstractSnippet
{

	/**
	 * 
	 */
	public JXErrorPaneDigressions()
	{
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void init()
	{
		super.init();
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception c)
		{
			c.printStackTrace();
		}
	}


	/**
	 * @see snippetlab.snippets.AbstractSnippet#method()
	 */
	@Override
	public void method()
	{
		
		Exception ee = new Exception("aiai");
//		JXErrorPane.showFrame(ee);
		ErrorInfo err = new ErrorInfo(
				"Title", "basic error",null, 
				null, null, Level.WARNING, null);
		
		JXErrorPane.showDialog(frame, err);

	}

}
