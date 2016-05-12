/*
 * ChainStatusBasicListCellRenderer.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.misc.ChainProgressDialog.impl;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import snippetlab.snippets.misc.ChainProgressDialog.interfaces.CommandWrapperInterface;

/**
 * 
 */
public class ChainStatusBasicListCellRenderer extends JLabel implements
		ListCellRenderer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DONE = 100;
	private static final int FAILED = -1;
	private static final String DONE_ICON = "png/done.gif";
	private static final String FAILED_ICON = "png/undone.gif";
	private static final int DONE_INDEX = 0;
	private static final int FAILED_INDEX = 1;
	private Icon[] icons = new Icon[2];

	/**
	 * 
	 */
	public ChainStatusBasicListCellRenderer()
	{
		setOpaque(true);
		icons[DONE_INDEX] = new ImageIcon(DONE_ICON);
		icons[FAILED_INDEX] = new ImageIcon(FAILED_ICON);
	}

	/**
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(	JList list,
													Object value,
													int index,
													boolean isSelected,
													boolean cellHasFocus)
	{
		Icon icon = null;

		if (isSelected)
		{
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		}
		else
		{
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		CommandWrapperInterface o = (CommandWrapperInterface) value;

		if (o.getStatus() == DONE)
		{
			icon = icons[DONE_INDEX];
		}
		else if(o.getStatus() == FAILED)
		{
			icon = icons[FAILED_INDEX];
		}
		else
		{
			icon = null;
		}

		setIcon(icon);
		setText(o.toString());
		setFont(list.getFont());
		
		list.revalidate();
		list.repaint();
		
		return this;
	}

}
