/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.documentsdigressions;


import javax.swing.event.DocumentEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

/**
 *
 * @author jtviegas
 */
public class CustomDocument extends DefaultStyledDocument
{
	private static final long serialVersionUID = -4150424747943491941L;
	private int maxSize = -1;

	/**
	 * Constructor do objecto EJSEDocument.
	 * 
	 * @param component
	 *            Descrição do parâmetro.
	 * @param maxSize
	 *            Descrição do parâmetro.
	 */
	public CustomDocument(int maxSize)
	{
		super();
		this.maxSize = maxSize;
	}

	/*
	 *
	 */
	/**
	 * Constructor do objecto EJSEDocument.
	 * 
	 * @param component
	 *            Descrição do parâmetro.
	 */
	public CustomDocument()
	{
		super();

	}


	/*
	 *
	 */
	/**
	 * Aqui deverá ser colocada a descrição do método.
	 * 
	 * @param offset
	 *            Descrição do parâmetro.
	 * @param str
	 *            Descrição do parâmetro.
	 * @param a
	 *            Descrição do parâmetro.
	 * @exception BadLocationException
	 *                Descrição da excepção.
	 */
    @Override
	public void insertString(int offset, String str, AttributeSet a) throws BadLocationException
	{

			if (maxSize != -1)
			{
				StringBuffer buff = new StringBuffer();
				buff.append(this.getText(0, this.getLength()));
				buff.append(str);
				if (buff.toString().trim().length() > maxSize)
				{
					return;
				}
			}
                        
		super.insertString(offset, str, a);

	}


	/*
	 * @return
	 */
	/**
	 * Obtém o atributo Text.
	 * 
	 * @return O valor do atributo Text.
	 */
	private String getText()
	{
		try
		{
			return this.getText(0, this.getLength());
		}
		catch (Exception e)
		{
			return "";
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.AbstractDocument#fireChangedUpdate(javax.swing.event.DocumentEvent)
	 */
//	@Override
//	protected void fireChangedUpdate(DocumentEvent e) {
//		// TODO Auto-generated method stub
//		super.fireChangedUpdate(e);
//		System.out.println("event:change text:" + this.getText());
//	}

	/* (non-Javadoc)
	 * @see javax.swing.text.AbstractDocument#fireInsertUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	protected void fireInsertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		super.fireInsertUpdate(e);
		System.out.println("event:insert text:" + this.getText());
	}

	/* (non-Javadoc)
	 * @see javax.swing.text.AbstractDocument#fireRemoveUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	protected void fireRemoveUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		super.fireRemoveUpdate(e);
		System.out.println("event:remove text:" + this.getText());
	}

	
	
	
}