/*
 * OnState.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.mmmodule.view.states;

import snippetlab.snippets.swing.mmmodule.view.View;



/**
 * 
 */
public class OnState
{
	private View view;
	/**
	 * 
	 */
	public OnState(View view)
	{
		this.view = view;
	}

	/**
	 * @see org.aprestos.code.bok.gui.interfaces.GUIState#change()
	 */
	public void change()
	{
		if(!view.getCk_on().isSelected())
		{
			view.getCb_attribute().setSelectedIndex(-1);
			view.getJl_items().clearSelection();
			view.getCb_attribute().setEnabled(false);
			view.getJl_items().setEnabled(false);
		}
		else
		{
			view.getCb_attribute().setEnabled(true);
			view.getJl_items().setEnabled(true);
		}
	}

}
