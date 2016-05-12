/**
 * 
 */
package snippetlab.snippets.misc.documentsdigressions;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import snippetlab.interfaces.SnippetInterface;

/**
 * @author joao.viegas
 *
 */
public class CustomDocumentSnippet implements SnippetInterface {

	JTextField tf = new JTextField();
	JTextField tf_2 = new JTextField();
	/**
	 * 
	 */
	public CustomDocumentSnippet() 
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see snippetlab.interfaces.SnippetInterface#go(javax.swing.JPanel, javax.swing.JFrame)
	 */
	@Override
	public void go(JPanel panel, JFrame frame) 
	{
		tf.setDocument(new CustomDocument());
		Document doc = new PlainDocument();
		doc.addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("change");
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("insert");
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("remove");	
			}});
		
		tf_2.setDocument(doc);
		panel.add(tf, BorderLayout.CENTER);
		panel.add(tf_2, BorderLayout.SOUTH);
		
	}

}
