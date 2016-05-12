package org.jtvatdsi.studies.struts.quotesui.forms;

import org.apache.struts.action.ActionForm;

@SuppressWarnings("deprecation")
public class SubmitForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String author;
	private String text;

	public SubmitForm() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	

}
