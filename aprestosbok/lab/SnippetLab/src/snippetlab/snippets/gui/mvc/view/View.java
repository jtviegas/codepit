/**
 * 
 */
package snippetlab.snippets.gui.mvc.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author joao.viegas
 * 
 */
public class View extends AbstractView 
{
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	protected JTextField tf_name;

	protected JTextField tf_director;

	protected JComboBox cb_genre;

	protected JList jl_actors;


	protected void initComponents() 
	{

		properties = new String[]{"name","director","genre","actors"}; 
		
		tf_name = new JTextField();
		tf_director = new JTextField();
		cb_genre = new JComboBox(new Object[] { "drama", "crime", "comic" });
		jl_actors = new JList();

		setLayout(new FormLayout("10dlu,50dlu,fill:100dlu:grow,10dlu",
				"10dlu,pref,10dlu,pref,10dlu,pref,10dlu,fill:50dlu:grow,10dlu,pref"));
		CellConstraints cc = new CellConstraints();

		add(new JLabel("name"), cc.xy(2, 2));

		add(tf_name, cc.xy(3, 2));
		add(new JLabel("director"), cc.xy(2, 4));
		add(tf_director, cc.xy(3, 4));
		add(new JLabel("genre"), cc.xy(2, 6));
		add(cb_genre, cc.xy(3, 6));
		add(new JLabel("actors"), cc.xy(2, 8));
		add(jl_actors, cc.xy(3, 8));

		Document textDocument = new PlainDocument();
		textDocument.addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent e){}

			@Override
			public void insertUpdate(DocumentEvent e) 
			{
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) 
			{
				
			}
		});
		tf_name.setDocument(textDocument);
		
		
	}

	/**
	 * @see snippetlab.snippets.gui.mvc.view.ViewInterface#getProperty(java.lang.String)
	 */
	@Override
	public Object getProperty(String name) 
	{
		Object result = null;

		if (name.equals(properties[0])) {
			result = tf_name.getText();
		}

		if (name.equals(properties[1])) {
			result = tf_director.getText();
		}

		if (name.equals(properties[2])) {
			result = cb_genre.getSelectedItem();
		}

		if (name.equals(properties[3])) 
		{
			List<String> actors = new ArrayList<String>();
			
			ListModel listModel = jl_actors.getModel();
			for (int i = 0; i < listModel.getSize(); i++) {
				actors.add((String) listModel.getElementAt(i));
			}

			result = actors;
		}

		return result;
	}

	/**
	 * @see snippetlab.snippets.gui.mvc.view.ViewInterface#setProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public void setProperty(String name, Object property) 
	{
		if (name.equals(properties[0])) {
			tf_name.setText((String) property);
		}

		if (name.equals(properties[1])) {
			tf_director.setText((String) property);
		}

		if (name.equals(properties[2])) {
			cb_genre.setSelectedItem(property);
		}

		if (name.equals(properties[3])) 
		{
			String[] actors = (String[]) property;

			DefaultListModel listModel = new DefaultListModel();
			for (int i = 0; i < actors.length; i++) {
				listModel.addElement((String) actors[i]);
			}

			jl_actors.setModel(listModel);

		}
	}
}
