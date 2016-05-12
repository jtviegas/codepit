/*
 * Model.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.widgets.table;

/**
 * 
 */
public class Model
{

	private int id;
	private String name;
	private boolean state;
	private String text;
	
	/**
	 * 
	 */
	public Model()
	{
		
	}

	/**
	 * @param id
	 * @param name
	 * @param state
	 * @param text
	 */
	public Model(int id, String name, boolean state, String text)
	{
		super();
		this.id = id;
		this.name = name;
		this.state = state;
		this.text = text;
	}

	/**
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the state
	 */
	public boolean isState()
	{
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(boolean state)
	{
		this.state = state;
	}

	/**
	 * @return the text
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text)
	{
		this.text = text;
	}

}
