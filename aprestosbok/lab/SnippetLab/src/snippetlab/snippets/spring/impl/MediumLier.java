package snippetlab.snippets.spring.impl;

import snippetlab.snippets.spring.interfaces.Lier;
//snippetlab.snippets.spring.impl.MediumLier
public class MediumLier implements Lier
{

	public MediumLier()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public String tellLie()
	{
		return "got myself a 4 feet (!!!) robalo yesterday";
	}

	public void init()
	{
		System.out.println("MediumLier.init()");
	}
	
	public void destroy()
	{
		System.out.println("MediumLier.destroy()");
	}
}
