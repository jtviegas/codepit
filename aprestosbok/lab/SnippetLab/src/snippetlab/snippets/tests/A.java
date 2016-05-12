package snippetlab.snippets.tests;

public class A
{
	protected String prop;
	
	public A()
	{
		// TODO Auto-generated constructor stub
	}

	public A(String _p)
	{
		this.prop = _p;
	}
	/**
	 * @return the prop
	 */
	public String getProp()
	{
		return prop;
	}

	/**
	 * @param prop the prop to set
	 */
	public void setProp(String prop)
	{
		this.prop = prop;
	}

}

class B extends A
{
	protected String attrib;

	/**
	 * @return the attrib
	 */
	public String getAttrib()
	{
		return attrib;
	}

	/**
	 * @param attrib the attrib to set
	 */
	public void setAttrib(String attrib)
	{
		this.attrib = attrib;
	}
	
}