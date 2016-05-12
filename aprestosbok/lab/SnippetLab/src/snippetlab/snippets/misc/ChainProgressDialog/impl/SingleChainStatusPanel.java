/*
 * SingleChainStatusPanel.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.misc.ChainProgressDialog.impl;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.apache.commons.chain.Command;

import snippetlab.snippets.misc.ChainProgressDialog.interfaces.ChainStatusPanelInterface;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 */
public class SingleChainStatusPanel extends JPanel implements
		ChainStatusPanelInterface
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
	/**
	 * 
	 */
	public SingleChainStatusPanel(){super();}

	public SingleChainStatusPanel(Command[] chain)
	{
		super();
	}

	/**
	 * @see snippetlab.snippets.misc.ChainProgressDialog.interfaces.ChainStatusPanelInterface#setChain(java.lang.Object[])
	 */
	@Override
	public void setChain(Command[] chain)
	{

	}

	
	public void init()
	{
		CellConstraints cc = new CellConstraints();
		
		this.setLayout(new FormLayout(
				"FILL:150:GROW", // Column
				"10dlu,FILL:50:grow," + // Row Upper Panel
						"10dlu"));
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		add(progressBar, cc.xy(1, 2));
		
	}
	/**
	 * @see snippetlab.snippets.misc.ChainProgressDialog.interfaces.ChainStatusPanelInterface#update(java.lang.Object, int)
	 */
	@Override
	public void update(Command command, int status)
	{
		progressBar.setValue(status);
	}

}
