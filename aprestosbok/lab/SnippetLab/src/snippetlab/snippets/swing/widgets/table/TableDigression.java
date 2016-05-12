/*
 * TableDigression.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.widgets.table;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import snippetlab.interfaces.SnippetInterface;

public class TableDigression implements SnippetInterface
{
	private TheTableModel tableModel = new TheTableModel();
	
	public TableDigression()
	{
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void go(JPanel panel, JFrame frame)
	{
		JTable table = new JTable(tableModel);
		panel.add(table, BorderLayout.CENTER);
		
		JButton ba=new JButton("put model A");
		JButton bb=new JButton("put model B");
		
		ba.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				tableModel.setData(Arrays.asList(new Model(5,"a",true,"xxxxxxxxxxxxxx"),
						new Model(4,"v",true,"vvvvvvvvvvvvvvv"),
						new Model(49,"sdf",true,"ffffffffffffffffff"),
						new Model(11,"cd",true,"6666666666666666666")
				));
			}
			
		});
		
		bb.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				tableModel.setData(Arrays.asList(new Model(2,"a",true,"skladfjdklasjfkl"),
						new Model(21,"v",true,"skladfbtu76rfkl"),
						new Model(56,"sdf",true,"svfdvfgfgbglasjfkl"),
						new Model(67,"cd",true,"skdfsderr4")
				));
			}
			
		});
		panel.add(ba, BorderLayout.WEST);
		panel.add(bb, BorderLayout.EAST);
		
		table.getColumnModel().getColumn(0).setCellRenderer(new TheCellRenderer());
		
	}

}
