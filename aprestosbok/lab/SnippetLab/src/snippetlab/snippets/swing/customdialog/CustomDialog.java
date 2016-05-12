/*
 * CustomDialog.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.customdialog;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import snippetlab.snippets.swing.MnemonicAndAcceleratorManager.MnemonicAndAcceleratorManager;
import snippetlab.snippets.swing.customdialog.exceptions.InitializationException;
import snippetlab.snippets.swing.customdialog.interfaces.ButtonPanelFactoryInterface;
import snippetlab.snippets.swing.customdialog.misc.CustomDialogAction;
import snippetlab.snippets.swing.customdialog.misc.CustomDialogType;
import snippetlab.snippets.swing.customdialog.misc.PropertyKey;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;


public abstract class CustomDialog extends JDialog
{

	protected static final String WIDTH="width";
	protected static final String HEIGHT="height";
	protected static final String X="x";
	protected static final String Y="y";

	protected CustomDialogAction userAction;
	protected Properties props;
	protected JScrollPane scrollPane;
	protected CustomDialogType type;


	public CustomDialog(JFrame owner, Properties props, CustomDialogType type)throws InitializationException
	{
		super(((owner == null) ? new JFrame() : owner), true);

		this.props = props;
		this.type = type;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initDialog();

	}

	public abstract void ok();

	public abstract void cancel();

	public abstract void help();
	
	public abstract void apply();
	
	public void setVisible(boolean visible)
	{
		if (!visible)
		{
			savePreferences();
		}
		super.setVisible(visible);
	}


	public void setVisibleToUser()
	{

		pack();
		setDialogMinimumSize();

		// All Dialogs that extend this one must define this method if
		// special preferences are required
		checkPreferences();

		//setLocationRelativeTo(owner);
		setResizable(true);
		setVisible(true);
	}	

	public void addPanel(JComponent panel)
	{
		scrollPane.setViewportView(panel);
	}

	
	
	public CustomDialogAction getUserAction()
	{
		return userAction;
	}

	protected void checkPreferences()
	{

		Preferences prefs = Preferences.userNodeForPackage(this.getClass());

		int width = prefs.getInt(WIDTH, 0);
		int height = prefs.getInt(HEIGHT, 0);
		int x = prefs.getInt(X, 0);
		int y = prefs.getInt(Y, 0);
	
		if(width > 0)
			setSize(width,height);

		if(x > 0)
			setLocation(x,y);

	}
	
	protected void savePreferences()
	{

		Preferences prefs = Preferences.userNodeForPackage(this.getClass());
		prefs.putInt(WIDTH, getSize().width);
		prefs.putInt(HEIGHT, getSize().height);
		prefs.putInt(X, getLocation().x);
		prefs.putInt(Y, getLocation().y);

	}

	@Override
	protected void processWindowEvent(WindowEvent e)
	{
		
		if(e.getID() == WindowEvent.WINDOW_CLOSING
				&&
			(null == userAction || userAction.equals(CustomDialogAction.HELP))		
		)
		{
				userAction = CustomDialogAction.CANCEL;
				cancel();
		}

	}
	
	private void setEscapeKey()
	{
		KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

		InputMap inputMap = getRootPane().getInputMap(
				JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		inputMap.put(escapeKey, CustomDialogAction.CANCEL);
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

	


	private final void initDialog() throws InitializationException
	{
		//load actions into action map
		loadActions();

		//build panel to set it to be content pane
		FormLayout layout = new FormLayout(
		// columns
				"5dlu, " + "FILL:pref:grow, " +
				// 2
						"5dlu",
				// rows
				"5dlu, " + "FILL:pref:grow, " +
				// 2 - Main Panel
						"5dlu, " + "pref, " +
						// 4 - Buttons Panel
						"5dlu");

		JPanel panel = new JPanel();
		panel.setLayout(layout);
		CellConstraints cc = new CellConstraints();
		
		scrollPane= new JScrollPane(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		scrollPane.setBorder(null);
		panel.add(scrollPane, cc.xy(2, 2));
		
		ButtonPanelFactoryInterface buttonPanelFactory =
			AbstractButtonPanelFactory.getFactory(this.type);
		buttonPanelFactory.setDialog(this);
		buttonPanelFactory.setProps(this.props);
		
		JPanel buttonPanel = buttonPanelFactory.getButtonPanel();

		panel.add(buttonPanel, cc.xy(2, 4));

		this.setContentPane(panel);

		setEscapeKey();

	}
	
	private void loadActions() throws InitializationException
	{
		try
		{
			MnemonicAndAcceleratorManager.initialize(props);
			
			Action ok = new AbstractAction(){
				private static final long serialVersionUID = 1L;
				@Override
				public void actionPerformed(ActionEvent e){
					userAction = CustomDialogAction.OK;
					ok();}
				};
				
			ok.putValue(AbstractAction.SMALL_ICON,
					new ImageIcon(props.getProperty(PropertyKey.OK_ICON.getKey())));
			ok.putValue(AbstractAction.NAME,
					props.getProperty(PropertyKey.OK_TEXT.getKey()));
			ok.putValue(AbstractAction.MNEMONIC_KEY,
					MnemonicAndAcceleratorManager.getInstance().getMnemonic(PropertyKey.OK_MNEMONIC.getKey()));
			ok.putValue(AbstractAction.ACCELERATOR_KEY,
					MnemonicAndAcceleratorManager.getInstance().getAccelerator(PropertyKey.OK_ACCELERATOR.getKey()));
			
			getRootPane().getActionMap().put(CustomDialogAction.OK, ok);
			
			Action help = new AbstractAction(){
				private static final long serialVersionUID = 1L;
				@Override
				public void actionPerformed(ActionEvent e){
					userAction = CustomDialogAction.HELP;
					help();}
				};
				
				help.putValue(AbstractAction.SMALL_ICON,
					new ImageIcon(props.getProperty(PropertyKey.HELP_ICON.getKey())));
				help.putValue(AbstractAction.NAME,
					props.getProperty(PropertyKey.HELP_TEXT.getKey()));
				help.putValue(AbstractAction.MNEMONIC_KEY,
					MnemonicAndAcceleratorManager.getInstance().getMnemonic(PropertyKey.HELP_MNEMONIC.getKey()));
				help.putValue(AbstractAction.ACCELERATOR_KEY,
					MnemonicAndAcceleratorManager.getInstance().getAccelerator(PropertyKey.HELP_ACCELERATOR.getKey()));
			
				getRootPane().getActionMap().put(CustomDialogAction.HELP, help);
				
			Action cancel = new AbstractAction(){
				private static final long serialVersionUID = 1L;
				@Override
				public void actionPerformed(ActionEvent e){
					userAction = CustomDialogAction.CANCEL;
					cancel();}
				};
				
				cancel.putValue(AbstractAction.SMALL_ICON,
					new ImageIcon(props.getProperty(PropertyKey.CANCEL_ICON.getKey())));
				cancel.putValue(AbstractAction.NAME,
					props.getProperty(PropertyKey.CANCEL_TEXT.getKey()));
				cancel.putValue(AbstractAction.MNEMONIC_KEY,
					MnemonicAndAcceleratorManager.getInstance().getMnemonic(PropertyKey.CANCEL_MNEMONIC.getKey()));
				cancel.putValue(AbstractAction.ACCELERATOR_KEY,
					MnemonicAndAcceleratorManager.getInstance().getAccelerator(PropertyKey.CANCEL_ACCELERATOR.getKey()));
			
				getRootPane().getActionMap().put(CustomDialogAction.CANCEL, cancel);
				
			Action apply = new AbstractAction(){
				private static final long serialVersionUID = 1L;
				@Override
				public void actionPerformed(ActionEvent e){
					userAction = CustomDialogAction.APPLY;
					apply();}
				};
				
				apply.putValue(AbstractAction.SMALL_ICON,
					new ImageIcon(props.getProperty(PropertyKey.APPLY_ICON.getKey())));
				apply.putValue(AbstractAction.NAME,
					props.getProperty(PropertyKey.APPLY_TEXT.getKey()));
				apply.putValue(AbstractAction.MNEMONIC_KEY,
					MnemonicAndAcceleratorManager.getInstance().getMnemonic(PropertyKey.APPLY_MNEMONIC.getKey()));
				apply.putValue(AbstractAction.ACCELERATOR_KEY,
					MnemonicAndAcceleratorManager.getInstance().getAccelerator(PropertyKey.APPLY_ACCELERATOR.getKey()));
			
			getRootPane().getActionMap().put(CustomDialogAction.APPLY, apply);
			
		}
		catch(Exception x)
		{
			throw new InitializationException(x);
		}

	}
	
}
