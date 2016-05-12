/*
 * UserMessageDialogDigressions.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing;

import java.util.Properties;

import snippetlab.snippets.AbstractSnippet;
import snippetlab.snippets.swing.usermessagedialog.UserMessageDialog;

/**
 * 
 */
public class UserMessageDialogDigressions extends AbstractSnippet
{

	/**
	 * 
	 */
	public UserMessageDialogDigressions()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see snippetlab.snippets.AbstractSnippet#method()
	 */
	@Override
	public void method()
	{
		UserMessageDialog.initialize(new Properties(), "app title", frame);
		
		UserMessageDialog.getInstance().showExceptionMessage("this is a message!");
		UserMessageDialog.getInstance().showInfoMessage("this is a message!");
		UserMessageDialog.getInstance().showWarningMessage("this is a message!");
		System.out.println(UserMessageDialog.getInstance().showOKCancelMessage("respondes?"));
		System.out.println(UserMessageDialog.getInstance().showYesNoMessage("respondes?"));
		UserMessageDialog.getInstance().showMessage("this is a message!");
	}

}
