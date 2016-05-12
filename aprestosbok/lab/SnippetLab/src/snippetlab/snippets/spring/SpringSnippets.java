package snippetlab.snippets.spring;

import java.io.IOException;

import org.springframework.beans.BeansException;

import snippetlab.snippets.AbstractSnippet;
import snippetlab.snippets.spring.interfaces.Fisher;

//snippetlab.snippets.spring.SpringSnippets
public class SpringSnippets extends AbstractSnippet
{
	@Override
	public void method()
	{
		one();
	}
	
	private void one()
	{
		Fisher fisher;
		
		try
		{
			fisher = (Fisher) SpringAppContext.getInstance().getContext().getBean("fisher");
			System.out.println(fisher.tellAboutLastFishSize());
			
		} 
		catch (BeansException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fisher = null;
	}


}
