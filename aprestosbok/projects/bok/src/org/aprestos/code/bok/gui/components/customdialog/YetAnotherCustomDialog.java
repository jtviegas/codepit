/*
 * YetAnotherCustomDialog.java
 */
/**
 * 
 */
package org.aprestos.code.bok.gui.components.customdialog;

import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JViewport;

import org.aprestos.code.bok.common.exceptions.InitializationException;
import org.aprestos.code.bok.gui.components.customdialog.interfaces.DialogActionsInterface;
import org.aprestos.code.bok.gui.components.customdialog.misc.CustomDialogType;

/**
 * YetAnotherCustomDialog is an extension from CustomDialog.
 * Features added:
 * <ul>
 * <li>ability for providing
 * 	a single interface on which is delegated the responsibility
 * 	for the user actions;
 * <li>a new method,<code>getViewPort()</code>, providing direct 
 * 	access to the scrollpane viewport.
 * </ul>
 * 
 * @see CustomDialog
 */
public class YetAnotherCustomDialog extends CustomDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DialogActionsInterface dialogActions;
	/**
	 * @param owner
	 * @param props
	 * @param type
	 * @throws InitializationException
	 */
	public YetAnotherCustomDialog(	JFrame owner,Properties props,
					CustomDialogType type,DialogActionsInterface dialogActions) 
						throws InitializationException
	{
		super(owner, props, type);
		this.dialogActions = dialogActions;
	}

	/**
	 * @see org.aprestos.code.bok.gui.components.customdialog.CustomDialog#apply()
	 */
	@Override
	public void apply()
	{
		dialogActions.apply();
	}

	/**
	 * @see org.aprestos.code.bok.gui.components.customdialog.CustomDialog#cancel()
	 */
	@Override
	public void cancel()
	{
		dialogActions.cancel();
	}

	/**
	 * @see org.aprestos.code.bok.gui.components.customdialog.CustomDialog#help()
	 */
	@Override
	public void help()
	{
		dialogActions.help();
	}

	/**
	 * @see org.aprestos.code.bok.gui.components.customdialog.CustomDialog#ok()
	 */
	@Override
	public void ok()
	{
		dialogActions.ok();
	}
	
	public JViewport getViewPort()
	{
		return scrollPane.getViewport();
	}

}
