/*
 * SplitPaneResizeDigression.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.splitpaneResize;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import snippetlab.interfaces.SnippetInterface;
import snippetlab.snippets.swing.scrollpaneResize.ExampleBigList;
import snippetlab.snippets.swing.scrollpaneResize.ExampleBigPanel;
import snippetlab.snippets.swing.scrollpaneResize.ExampleSmallList;
import snippetlab.snippets.swing.scrollpaneResize.ExampleSmallPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * snippetlab.snippets.swing.splitpaneResize.SplitPaneResizeDigression
 */
public class SplitPaneResizeDigression implements SnippetInterface
{
	JScrollPane sp;
	/**
	 * 
	 */
	public SplitPaneResizeDigression()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see snippetlab.interfaces.SnippetInterface#go(javax.swing.JPanel, javax.swing.JFrame)
	 */
	@Override
	public void go(JPanel panel, JFrame frame)
	{
		
		JPanel southPanel=new JPanel(new FormLayout
				(
						"fill:pref:grow",
						"20dlu,20dlu"
				));
		
		JToolBar t=new JToolBar();
		Action ac = new AbstractAction()
		{@Override public void actionPerformed(ActionEvent e)
		{loadBigList();}};
		ac.putValue(AbstractAction.NAME, "BigList");
		t.add(ac);
		
		JPanel status=new JPanel(new FlowLayout());
		status.add(new JLabel("I'm fine, thanks!"));
		
		
		JPanel buttons = new JPanel(
				new FormLayout
				(
						"5dlu,pref,5dlu,pref,5dlu,pref,5dlu,pref,5dlu",
						"30dlu"
				)
				);

		CellConstraints cc = new CellConstraints();
		
		southPanel.add(buttons,cc.xy(1, 1));
		southPanel.add(status,cc.xy(1, 2));
		
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
		JSplitPane spl = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
		
		spl.add(new JScrollPane(new ExampleBigList()), 0);
		spl.add(sp, 1);
		
		panel.add(t, BorderLayout.NORTH);
		panel.add(spl, BorderLayout.CENTER);
		panel.add(southPanel, BorderLayout.SOUTH);
		
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