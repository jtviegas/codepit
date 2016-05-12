/*
 * ScrollPaneResizeDigressions.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.scrollpaneResize;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import snippetlab.interfaces.SnippetInterface;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * snippetlab.snippets.swing.scrollpaneResize.ScrollPaneResizeDigressions
 */
public class ScrollPaneResizeDigressions implements SnippetInterface
{
	JScrollPane sp;
	/**
	 * 
	 */
	public ScrollPaneResizeDigressions()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see snippetlab.interfaces.SnippetInterface#go(javax.swing.JPanel, javax.swing.JFrame)
	 */
	@Override
	public void go(JPanel panel, JFrame frame)
	{
		
		JPanel buttons = new JPanel(
				new FormLayout
				(
						"5dlu,pref,5dlu,pref,5dlu,pref,5dlu,pref,5dlu",
						"30dlu"
				)
				);
		CellConstraints cc = new CellConstraints();
		
		buttons.add(new JButton(new AbstractAction()
		{@Override public void actionPerformed(ActionEvent e)
		{loadBigList();}}), cc.xy(2, 1));
		buttons.add(new JButton(new AbstractAction()
		{@Override public void actionPerformed(ActionEvent e)
		{loadSmallList();}}), cc.xy(4, 1));
		buttons.add(new JButton(new AbstractAction()
		{@Override public void actionPerformed(ActionEvent e)
		{loadBigPanel();}}), cc.xy(6, 1));
		buttons.add(new JButton(new AbstractAction()
		{@Override public void actionPerformed(ActionEvent e)
		{loadSmallPanel();}}), cc.xy(8, 1));
		
		sp = new JScrollPane();
		
		panel.add(sp, BorderLayout.CENTER);
		panel.add(buttons, BorderLayout.SOUTH);
	}
	
	private void loadBigList()
	{
		sp.setViewportView(new ExampleBigList());
	}
	
	private void loadSmallList()
	{
		sp.setViewportView(new ExampleSmallList());
	}
	
	private void loadBigPanel()
	{
		sp.setViewportView(new ExampleBigPanel());
	}
	
	private void loadSmallPanel()
	{
		sp.setViewportView(new ExampleSmallPanel());
	}

}
