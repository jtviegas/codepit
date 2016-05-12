/*
 * TheTableModel.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.widgets.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * 
 */
public class TheTableModel extends AbstractTableModel
{
	String[] columns = new String[]{"id", "name", "state", "text"};
	List<Model> data = new ArrayList<Model>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public TheTableModel()
	{
		
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount()
	{
		return columns.length;
	}

	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount()
	{
		return data.size();
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column)
	{
		return columns[column];
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Object result = null;
		
		Model model = data.get(rowIndex);
		
		if(null != model)
		{
			switch(columnIndex)
			{
				case 0:
					result = model.getId();
					break;
				case 1:
					result = model.getName();
					break;
				case 2:
					result = model.isState();
					break;
				case 3:
					result = model.getText();
					break;
			}	
		}
		
		
		return result;
	}
	
	public Class<?> getColumnClass(int c) 
	{
        return getValueAt(0, c).getClass();
    }

	public void setData(List<Model> data)
	{
		this.data = data;
		this.fireTableDataChanged();
	}
	
}
