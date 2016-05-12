/*
 * ChainProgressDialog.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.misc.ChainProgressDialog;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.apache.commons.chain.Command;

import snippetlab.snippets.misc.ChainProgressDialog.impl.PanelFactory;
import snippetlab.snippets.misc.ChainProgressDialog.interfaces.ChainStatusPanelInterface;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Dialog notifying the supplied chain 
 * execution progress.
 */
public class ChainProgressDialog extends JDialog
{
	private static final int DEFAULT_WINDOW_HEIGHT = 300;
	private static final int DEFAULT_WINDOW_WIDTH = 200;
	private static final int DEFAULT_WINDOW_X = 0;
	private static final int DEFAULT_WINDOW_Y = 0;
	
	private static final int FAILED=-1;
	private static final int IDLE=0;
	private static final int STARTED=1;
	private static final int DONE=100;
	
	private static enum PreferencesKeys{WINDOW_HEIGHT,WINDOW_WIDTH,WINDOW_X,WINDOW_Y}
	
	private static final long serialVersionUID = 1L;
	private Command[] chain;
	private JButton ok;
	private JButton cancel;
	private boolean OKed;
	private volatile boolean cancelCall = false;
	private ChainStatusPanelInterface statusPanel;
	
	/**
	 * @param owner
	 */
	public ChainProgressDialog(Window owner)
	{
		super(owner);
	}

	public ChainProgressDialog(Window owner, Command[] chain)
	{
		super(owner);
		this.chain = chain;
		
	}
	
	public void setChain(Command[] chain)
	{
		this.chain = chain;
	}

	public void init()
	{
		initComponents();
	}
		
	private void initComponents()
	{
	
		
		FormLayout layout = new FormLayout(
		// columns
				"1dlu, " + "FILL:pref:grow, " +
				// 2
						"1dlu",
				// rows
				"1dlu, " + "FILL:pref:grow, " +
				// 2 - Main Panel
						"1dlu, " + "pref, " +
						// 4 - Buttons Panel
						"1dlu");

		PanelBuilder builder = new PanelBuilder(layout);

		builder.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();
		

		ok = new JButton("ok");
		ok.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ok();
			}
		});

		cancel = new JButton("cancel");
		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cancel();
			}
		});


		ok.setPreferredSize(cancel.getPreferredSize());

		FormLayout buttonLayout = new FormLayout(
				"FILL:pref:grow, " + 
				"pref, " + // ok
				"1dlu," + "pref"  // cancel
				, "pref");

		JPanel buttonPanel = new JPanel();
		
		buttonPanel.setLayout(buttonLayout);

			buttonPanel.add(ok, cc.xy(2, 1));
			buttonPanel.add(cancel, cc.xy(4, 1));
			
			
			if(chain.length > 1)
				statusPanel=PanelFactory.getFactory(PanelFactory.PanelType.PLURAL).createPanel();
			else
				statusPanel=PanelFactory.getFactory(PanelFactory.PanelType.SINGLE).createPanel();
			
			statusPanel.setChain(chain);
			statusPanel.init();
			
		builder.add((Component)statusPanel, cc.xy(2, 2));
		builder.add(buttonPanel, cc.xy(2, 4));
		
		this.setContentPane(builder.getPanel());
		setEscapeKey();
		addWindowListener(new DialogWindowAdapter());
		
	}
	
	public void ok()
	{
		OKed=true;
		savePreferences();
		setVisible(false);
	}
	
	private void go()
	{
		SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>(){

			@Override
			protected Void doInBackground() throws Exception
			{
				Void result=null;
				
				for(Command c:chain)
				{
					if(cancelCall)
						break;
					
					notifyEDT(c, STARTED);
					try
					{
						if(c.execute(null))
							notifyEDT(c, DONE);
						else
							notifyEDT(c, FAILED);
					}
					catch(Exception x)
					{
						x.printStackTrace();
						System.out.print("oops");
						notifyEDT(c, FAILED);
					}
				}
				return result;
			}

			@Override
			protected void done()
			{
				(ChainProgressDialog.this).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				ok.setEnabled(true);
				OKed=true;
				cancel.setEnabled(false);
			}

		}; 
			
		(ChainProgressDialog.this).setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		worker.execute();
	}
	
	private void notifyEDT(final Command c, final int status)
	{
		SwingUtilities.invokeLater(new Runnable()
		{

			@Override
			public void run()
			{
				statusPanel.update(c, status);
				
			}
			
		});
	}
	
	public void cancel()
	{
		System.out.println("window is going to close");
		if (cancelAction())
		{
			System.out.println("going to cancel");
			savePreferences();
			setVisible(false);
		}
	}
	
	private boolean cancelAction()
	{

		boolean result=false;
		
		if (reallyWantToCancel())
		{
			System.out.println("going to cancel worker thread");
			
			cancelCall = true;
			
			result = true;
		}

		return result;

	}
	
	private class DialogWindowAdapter extends WindowAdapter
	{
		/**
		 * @see java.awt.event.WindowAdapter#windowOpened(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowOpened(WindowEvent e)
		{
			ok.setEnabled(false);
			go();
		}

		/**
		 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
		 */
		@Override
		public void windowClosing(WindowEvent e)
		{

			
			System.out.println("window is going to close");
			if (!OKed && cancelAction())
			{
				System.out.println("window closing in cancel action");
				super.windowClosing(e);
			}
			else{
				System.out.println("window closing NOT in cancel action");
				;
			}
			savePreferences();

		}

	}
	
	public void showToUser()
	{
		getRootPane().setDefaultButton(ok);
		pack();
		setDialogMinimumSize();
		checkPreferences();
		setLocationRelativeTo(this.getOwner());
		setResizable(true);
		setVisible(true);
	}
	
	private void setEscapeKey()
	{
		Action actionCancel = new AbstractAction()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ev)
			{
				cancel();
			}
		};
		actionCancel.putValue(AbstractAction.NAME, "EscapeKeyActionName");
		Object actionCancelName = actionCancel.getValue(AbstractAction.NAME);

		KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

		getRootPane().getActionMap().put(actionCancelName, actionCancel);

		InputMap inputMap = getRootPane().getInputMap(
				JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		inputMap.put(escapeKey, actionCancelName);
	}
	
	private void setDialogMinimumSize()
	{
		final Dimension minimumSize = getSize();

		setMinimumSize(minimumSize);

		ComponentAdapter componentAdapter = new ComponentAdapter()
		{
			public void componentResized(ComponentEvent ev)
			{
				Window tmp = (Window) ev.getSource();
				if (tmp.getWidth() < minimumSize.width
						|| tmp.getHeight() < minimumSize.height)
				{
					tmp.setSize(minimumSize.width, minimumSize.height);
				}
			}
		};

		addComponentListener(componentAdapter);
	}

	public void checkPreferences()
	{

		Preferences prefs = Preferences
				.userNodeForPackage(getClass());

		setSize(prefs.getInt(
				PreferencesKeys.WINDOW_WIDTH.toString(), DEFAULT_WINDOW_WIDTH),
				prefs.getInt(PreferencesKeys.WINDOW_HEIGHT
						.toString(), DEFAULT_WINDOW_HEIGHT));

		setLocation(prefs.getInt(
				PreferencesKeys.WINDOW_X.toString(), DEFAULT_WINDOW_X),
				prefs.getInt(PreferencesKeys.WINDOW_Y.toString(),
						DEFAULT_WINDOW_Y));

	}

	/**
	 * @see ejse.dialogs.EJSEDialog#savePreferences()
	 */

	public void savePreferences()
	{

		Preferences prefs = Preferences.userNodeForPackage(getClass());
		
		prefs.putInt(PreferencesKeys.WINDOW_WIDTH.toString(),
				getSize().width);
		prefs.putInt(PreferencesKeys.WINDOW_HEIGHT.toString(),
				getSize().height);
		prefs.putInt(PreferencesKeys.WINDOW_X.toString(),
				getLocation().x);
		prefs.putInt(PreferencesKeys.WINDOW_Y.toString(),
				getLocation().y);

	}
	private boolean reallyWantToCancel()
	{

		boolean result = false;

		if (JOptionPane.showConfirmDialog(this, 
				"Please, confirm you want to cancel.",
				"Confirmation required",
				JOptionPane.OK_CANCEL_OPTION) == 0)
		{
			result = true;
		}
		
		return result;
	}
}
