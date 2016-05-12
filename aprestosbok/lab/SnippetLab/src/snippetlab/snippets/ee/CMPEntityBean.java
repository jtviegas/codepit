package snippetlab.snippets.ee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import snippetlab.interfaces.SnippetInterface;

public class CMPEntityBean implements SnippetInterface, ActionListener
{

//	private StockList _stockList;
	private JTextField _ticker = new JTextField();
	private JTextField _name = new JTextField();
	private JButton _get = new JButton("Get");
	private JButton _add = new JButton("Add");
	private JButton _update = new JButton("Update");
	private JButton _delete = new JButton("Delete");
	private JFrame frame;

	@Override
	public void go(JPanel panel, JFrame frame)
	{
//		this.frame = frame;
//
//		// Get the stock lister
//		_stockList = getStockList();
//		// Add the title
//		JLabel title = new JLabel("Stock List");
//		title.setHorizontalAlignment(JLabel.CENTER);
//		panel.add(title, BorderLayout.NORTH);
//		// Add the stock label panel
//		JPanel stockLabelPanel = new JPanel(new GridLayout(2, 1));
//		stockLabelPanel.add(new JLabel("Symbol"));
//		stockLabelPanel.add(new JLabel("Name"));
//		panel.add(stockLabelPanel, BorderLayout.WEST);
//
//		// Add the stock field panel
//		JPanel stockFieldPanel = new JPanel(new GridLayout(2, 1));
//		stockFieldPanel.add(_ticker);
//		stockFieldPanel.add(_name);
//		panel.add(stockFieldPanel, BorderLayout.CENTER);
//		// Add the buttons
//		JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
//		_get.addActionListener(this);
//		buttonPanel.add(_get);
//		_add.addActionListener(this);
//		buttonPanel.add(_add);
//		_update.addActionListener(this);
//		buttonPanel.add(_update);
//		_delete.addActionListener(this);
//		buttonPanel.add(_delete);
//		panel.add(buttonPanel, BorderLayout.SOUTH);
//
//		frame.setSize(330, 130);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
//		// If get was clicked, get the stock
//		if (ae.getSource() == _get)
//		{
//			getStock();
//		}
//		// If add was clicked, add the stock
//		if (ae.getSource() == _add)
//		{
//			addStock();
//		}
//		// If update was clicked, update the stock
//		if (ae.getSource() == _update)
//		{
//			updateStock();
//		}
//		// If delete was clicked, delete the stock
//		if (ae.getSource() == _delete)
//		{
//			deleteStock();
//		}
	}

//	private StockList getStockList()
//	{
//		StockList stockList = null;
//		try
//		{
//			Properties properties = new Properties();
////		    properties.put("java.naming.factory.initial",
////		                  "com.sun.jndi.cosnaming.CNCtxFactory");
//		    
//		    properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
//		    properties.put(Context.PROVIDER_URL, "iiop://localhost:9037");
//		    properties.put(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming");
//		    properties.put(Context.STATE_FACTORIES, "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
//		    
//		    
//			// Get a naming context
//			InitialContext ctx = new InitialContext(properties);
//			
//			// Get a StockList object
//			stockList = (StockList) ctx.lookup(StockList.class.getName());
//			
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		return stockList;
//	}

//	private void getStock()
//	{
//		// Get the ticker
//		String ticker = _ticker.getText();
//		if (ticker == null || ticker.trim().length() == 0)
//		{
//			JOptionPane.showMessageDialog(this.frame, "Ticker is required");
//			return;
//		}
//		// Get the stock
//		try
//		{
//			String name = _stockList.getStock(ticker.trim());
//			_name.setText(name);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	private void addStock()
//	{
//		// Get the ticker
//		String ticker = _ticker.getText();
//		if (ticker == null || ticker.trim().length() == 0)
//		{
//			JOptionPane.showMessageDialog(this.frame, "Ticker is required");
//			return;
//		}
//
//		// Get the name
//		String name = _name.getText();
//		if (name == null || name.trim().length() == 0)
//		{
//			JOptionPane.showMessageDialog(this.frame, "Name is required");
//			return;
//		}
//		// Add the stock
//		try
//		{
//			_stockList.addStock(ticker.trim(), name.trim());
//			JOptionPane.showMessageDialog(this.frame, "Stock added!");
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	private void updateStock()
//	{
//		// Get the ticker
//		String ticker = _ticker.getText();
//		if (ticker == null || ticker.trim().length() == 0)
//		{
//			JOptionPane.showMessageDialog(this.frame, "Ticker is required");
//			return;
//		}
//		// Get the name
//		String name = _name.getText();
//		if (name == null || name.trim().length() == 0)
//		{
//			JOptionPane.showMessageDialog(this.frame, "Name is required");
//			return;
//		}
//		// Update the stock
//		try
//		{
//			_stockList.updateStock(ticker.trim(), name.trim());
//			JOptionPane.showMessageDialog(this.frame, "Stock updated!");
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	private void deleteStock()
//	{
//		// Get the ticker
//		String ticker = _ticker.getText();
//		if (ticker == null || ticker.trim().length() == 0)
//		{
//			JOptionPane.showMessageDialog(this.frame, "Ticker is required");
//			return;
//		}
//		// Delete the stock
//		try
//		{
//			_stockList.deleteStock(ticker.trim());
//			JOptionPane.showMessageDialog(this.frame, "Stock deleted!");
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
}
