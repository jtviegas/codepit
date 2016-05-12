/*
 * UserMessageDialog.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.usermessagedialog;

import java.awt.Component;
import java.awt.Window;
import java.util.Properties;

import javax.swing.JOptionPane;



public class UserMessageDialog
{
	private final String EXCEPTION_SUFFIX_KEY = "mmhs.mgmc.common.usermessagedialog.title.suffix.exception";
	private final String INFO_SUFFIX_KEY = "mmhs.mgmc.common.usermessagedialog.title.suffix.info";
	private final String WARNING_SUFFIX_KEY = "mmhs.mgmc.common.usermessagedialog.title.suffix.warning";
	private final String QUESTION_SUFFIX_KEY = "mmhs.mgmc.common.usermessagedialog.title.suffix.question";
	private final String MESSAGE_SUFFIX_KEY = "mmhs.mgmc.common.usermessagedialog.title.suffix.message";
	private Window parent;
	private String title;
	private Properties p;
	
	private static UserMessageDialog dialog;
	
	private UserMessageDialog(){}
	
	public static void initialize(Properties props, String title, Window owner)
	{
		dialog = new UserMessageDialog();
		dialog.p = props;
		dialog.title = title;
		dialog.parent = owner;
	}
	
	public static UserMessageDialog getInstance()
	{
		return dialog;
	}

	public void showMessage(Component owner, String message)
	{
		JOptionPane.showMessageDialog(owner, message, title + " - " + languageValue(MESSAGE_SUFFIX_KEY), JOptionPane.PLAIN_MESSAGE);
	}
	
	public void showLanguageMessage(Component owner, String key)
	{
		JOptionPane.showMessageDialog(owner, languageValue(key), title + " - " + languageValue(MESSAGE_SUFFIX_KEY), JOptionPane.PLAIN_MESSAGE);
	}
	
	public void showMessage(String message)
	{
		JOptionPane.showMessageDialog(parent, message, title + " - " + languageValue(MESSAGE_SUFFIX_KEY), JOptionPane.PLAIN_MESSAGE);
	}
	
	public void showLanguageMessage(String key)
	{
		JOptionPane.showMessageDialog(parent, languageValue(key), title + " - " + languageValue(MESSAGE_SUFFIX_KEY), JOptionPane.PLAIN_MESSAGE);
	}
	
	public int showYesNoMessage(Component owner, String message)
	{
		int result = -1;
		result = JOptionPane.showConfirmDialog(owner, message, title + " - " + languageValue(QUESTION_SUFFIX_KEY), JOptionPane.YES_NO_OPTION);
		return result;
	}
	
	public int showYesNoLanguageMessage(Component owner, String key)
	{
		int result = -1;
		result = JOptionPane.showConfirmDialog(owner, languageValue(key), title + " - " + languageValue(QUESTION_SUFFIX_KEY), JOptionPane.YES_NO_OPTION);
		return result;
	}
	
	public int showYesNoMessage(String message)
	{
		int result = -1;
		result = JOptionPane.showConfirmDialog(parent, message, title + " - " + languageValue(QUESTION_SUFFIX_KEY), JOptionPane.YES_NO_OPTION);
		return result;
	}
	
	public int showYesNoLanguageMessage(String key)
	{
		int result = -1;
		result = JOptionPane.showConfirmDialog(parent, languageValue(key), title + " - " + languageValue(QUESTION_SUFFIX_KEY), JOptionPane.YES_NO_OPTION);
		return result;
	}
	
	public int showOKCancelMessage(Component owner, String message)
	{
		int result = -1;
		result = JOptionPane.showConfirmDialog(owner, message, title + " - " + languageValue(QUESTION_SUFFIX_KEY), JOptionPane.OK_CANCEL_OPTION);
		return result;
	}
	
	public int showOKCancelLanguageMessage(Component owner, String key)
	{
		int result = -1;
		result = JOptionPane.showConfirmDialog(owner, languageValue(key), title + " - " + languageValue(QUESTION_SUFFIX_KEY), JOptionPane.OK_CANCEL_OPTION);
		return result;
	}
	
	public int showOKCancelMessage(String message)
	{
		int result = -1;
		result = JOptionPane.showConfirmDialog(parent, message, title + " - " + languageValue(QUESTION_SUFFIX_KEY), JOptionPane.OK_CANCEL_OPTION);
		return result;
	}
	
	public int showOKCancelLanguageMessage(String key)
	{
		int result = -1;
		result = JOptionPane.showConfirmDialog(parent, languageValue(key), title + " - " + languageValue(QUESTION_SUFFIX_KEY), JOptionPane.OK_CANCEL_OPTION);
		return result;
	}
	
	public void showWarningMessage(Component owner, String message)
	{
		JOptionPane.showMessageDialog(owner, message, title + " - " + languageValue(WARNING_SUFFIX_KEY) , JOptionPane.WARNING_MESSAGE);
	}
	
	public void showWarningLanguageMessage(Component owner, String key)
	{
		JOptionPane.showMessageDialog(owner, languageValue(key), title + " - " + languageValue(WARNING_SUFFIX_KEY) , JOptionPane.WARNING_MESSAGE);
	}
	
	public void showWarningMessage(String message)
	{
		JOptionPane.showMessageDialog(parent, message, title + " - " + languageValue(WARNING_SUFFIX_KEY) , JOptionPane.WARNING_MESSAGE);
	}
	
	public void showWarningLanguageMessage(String key)
	{
		JOptionPane.showMessageDialog(parent, languageValue(key), title + " - " + languageValue(WARNING_SUFFIX_KEY) , JOptionPane.WARNING_MESSAGE);
	}
	
	public void showInfoMessage(Component owner, String message)
	{
		JOptionPane.showMessageDialog(owner, message, title + " - " + languageValue(INFO_SUFFIX_KEY) , JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showInfoLanguageMessage(Component owner, String key)
	{
		JOptionPane.showMessageDialog(owner, languageValue(key), title + " - " + languageValue(INFO_SUFFIX_KEY) , JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showInfoMessage(String message)
	{
		JOptionPane.showMessageDialog(parent, message, title + " - " + languageValue(INFO_SUFFIX_KEY) , JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showInfoLanguageMessage(String key)
	{
		JOptionPane.showMessageDialog(parent, languageValue(key), title + " - " + languageValue(INFO_SUFFIX_KEY) , JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showExceptionMessage(String message)
	{
		JOptionPane.showMessageDialog(parent, message, title + " - " + languageValue(EXCEPTION_SUFFIX_KEY), JOptionPane.ERROR_MESSAGE);
	}
	
	public void showExceptionMessage(Component owner, String message)
	{
		JOptionPane.showMessageDialog(owner, message, title + " - " + languageValue(EXCEPTION_SUFFIX_KEY), JOptionPane.ERROR_MESSAGE);
	}
	
	public void showExceptionLanguageMessage(String key)
	{
		JOptionPane.showMessageDialog(parent, languageValue(key), title + " - " + languageValue(EXCEPTION_SUFFIX_KEY), JOptionPane.ERROR_MESSAGE);
	}

	public void showExceptionLanguageMessage(Component owner, String key)
	{
		JOptionPane.showMessageDialog(owner, languageValue(key), title + " - " + languageValue(EXCEPTION_SUFFIX_KEY), JOptionPane.ERROR_MESSAGE);
	}
	
	private String languageValue(String key)
	{
		String result = null;
		
		if(key.equals(EXCEPTION_SUFFIX_KEY))
			result="excepção";
		else if (key.equals(INFO_SUFFIX_KEY))
			result="informação";
		else if(key.equals(WARNING_SUFFIX_KEY))
			result="aviso";
		else if(key.equals(QUESTION_SUFFIX_KEY))
			result="questão";
		else if(key.equals(MESSAGE_SUFFIX_KEY))
			result="nota";

		return result;
	}

	
}
