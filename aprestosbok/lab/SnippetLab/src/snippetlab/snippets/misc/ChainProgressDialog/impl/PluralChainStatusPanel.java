/*
 * ChainStatusPanel.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.misc.ChainProgressDialog.impl;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import org.apache.commons.chain.Command;

import snippetlab.snippets.misc.ChainProgressDialog.interfaces.ChainStatusPanelInterface;
import snippetlab.snippets.misc.ChainProgressDialog.interfaces.CommandWrapperInterface;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 */
public class PluralChainStatusPanel extends JPanel implements
		ChainStatusPanelInterface
{

	/**
	 * 
	 */
	private static final int IDLE=0;
	private static final int DONE=100;
	private static final int STARTED=1;
	private static final long serialVersionUID = 1L;
	private CommandWrapperInterface[] chain;
	private ListCellRenderer renderer = new ChainStatusBasicListCellRenderer();
	private JList list;
	private JProgressBar progressBar;
	
	

	
	/**
	 * 
	 */
	public PluralChainStatusPanel(){super();}

	public PluralChainStatusPanel(Command[] chain)
	{
		super();
		loadChain(chain);
	}
	
	public PluralChainStatusPanel(Command[] chain, ListCellRenderer renderer)
	{
		super();
		loadChain(chain);
		this.renderer = renderer;
	}
	/**
	 * @see snippetlab.snippets.misc.ChainProgressDialog.interfaces.ChainStatusPanelInterface#setChain(java.lang.Object[])
	 */
	@Override
	public void setChain(Command[] chain)
	{
		loadChain(chain);
	}

	public void setListCellRenderer (ListCellRenderer renderer)
	{
		this.renderer = renderer;
	}
	
	public void init()
	{
		CellConstraints cc = new CellConstraints();
		
		this.setLayout(new FormLayout(
				"FILL:150:GROW", // Column
				"10dlu,FILL:150:grow," + // Row Upper Panel
						"10dlu,fill:30:grow," + // separator
						"10dlu"));
		
		list = new JList(this.chain);
		list.setCellRenderer(this.renderer);
		
		add(new JScrollPane(list), cc.xy(1, 2));
		progressBar = new JProgressBar(IDLE, DONE);
		progressBar.setValue(IDLE);
		progressBar.setStringPainted(true);
		add(progressBar, cc.xy(1, 4));
		
	}
	/**
	 * @see snippetlab.snippets.misc.ChainProgressDialog.interfaces.ChainStatusPanelInterface#update(java.lang.Object, int)
	 */
	@Override
	public void update(Command command, int status)
	{
		int index = searchCommandWrapper(command);
		
		if(index > -1)
		{
			CommandWrapperInterface wrapper = chain[index];
			wrapper.setStatus(status);
			
			if(status == DONE)
			{
				progressBar.setValue(calcPercentage(index));
			}
			
			if(status == STARTED)
			{
				list.setSelectedValue(wrapper, true);
			}
			
		}
	}

	private void loadChain(Command[] chain)
	{
		int index=0;
		this.chain = new CommandWrapperInterface[chain.length];
		for(Command c:chain)
		{
			this.chain[index++] = new CommandWrapperImpl(c);
		}
	}
	
	private int searchCommandWrapper(Command command)
	{
		int result = -1;
		
		int index = 0;
		for(CommandWrapperInterface c:chain)
		{
			if(c.getCommand().equals(command))
			{
				result = index;
				break;
			}
			index++;
		}
		
		return result;
	}
	
	private int calcPercentage(int index)
	{
		int result=0;
		
		//indexes are zero based so...
		float n = index+1;
		
		float calc = (n / chain.length) * DONE;
		result = Math.round(calc);
		
		return result;
	}
}
