/**
 * 
 */
package snippetlab.snippets.spring.impl;

import snippetlab.snippets.spring.interfaces.Fisher;
import snippetlab.snippets.spring.interfaces.Lier;

/**
 * @author joao.viegas
 *
 */
public class CarcavelosFisher implements Fisher
{
	private Lier lier;
	
	/**
	 * 
	 */
	public CarcavelosFisher()
	{
		// TODO Auto-generated constructor stub
	}
	
	public CarcavelosFisher(Lier lier)
	{
		this.lier = lier;
	}
	
	
	/* (non-Javadoc)
	 * @see snippetlab.snippets.spring.interfaces.Fisher#tellAboutLastFishSize()
	 */
	@Override
	public String tellAboutLastFishSize()
	{
		return lier.tellLie();
	}

}
