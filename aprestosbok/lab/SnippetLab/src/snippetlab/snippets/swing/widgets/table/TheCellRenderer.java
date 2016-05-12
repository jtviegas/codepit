/*
 * TheCellRenderer.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.widgets.table;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 */
public class TheCellRenderer extends DefaultTableCellRenderer
{
	private static int counter=0;
	private Icon icon1,icon2;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TheCellRenderer()
	{
		icon1=new ImageIcon("png/doc.png");
		icon2=new ImageIcon("png/moz.png");
	}
	
	/**
	 * @see javax.swing.table.DefaultTableCellRenderer#setValue(java.lang.Object)
	 */
	@Override
	protected void setValue(Object value)
	{
		super.setValue(value);
		
		if(0 == counter % 2)
			super.setIcon(icon1);
		else
			super.setIcon(icon2);
				
		counter++;
	}

}
