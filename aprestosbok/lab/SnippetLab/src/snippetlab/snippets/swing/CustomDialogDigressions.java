/*
 * CustomDialogDigressions.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing;

import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;

import snippetlab.snippets.AbstractSnippet;
import snippetlab.snippets.swing.customdialog.CustomDialog;
import snippetlab.snippets.swing.customdialog.exceptions.InitializationException;
import snippetlab.snippets.swing.customdialog.misc.CustomDialogType;

public class CustomDialogDigressions extends AbstractSnippet
{

	public CustomDialogDigressions()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void method()
	{
		
		try
		{
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("snippetlab/snippetlab.properties");
			
			Properties p = new Properties();
			p.load(is);
			
			MyDialog d = new MyDialog(frame,p,CustomDialogType._OK_);
			d.addPanel(new JPanel());
			d.setVisibleToUser();
			
		}
		catch(Exception x)
		{
			x.printStackTrace();
		}
		
		

	}

	
	private class MyDialog extends CustomDialog
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyDialog(JFrame owner, Properties props, CustomDialogType type) throws InitializationException
		{
			super(owner, props, type);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void apply()
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void cancel()
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void help()
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void ok()
		{
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
