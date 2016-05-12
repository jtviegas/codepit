package snippetlab.snippets.concurrency.jthreads2ed;

import java.awt.BorderLayout;

import snippetlab.snippets.AbstractSnippet;
import snippetlab.snippets.concurrency.jthreads2ed.one.OurApplet;
import snippetlab.snippets.concurrency.jthreads2ed.two.Animate;

public class JThreads2Ed extends AbstractSnippet
{
	int count=0;

	OurApplet _o = new OurApplet();
	
	//snippetlab.snippets.concurrency.jthreads2ed.JThreads2Ed
	public JThreads2Ed()
	{
		
	}

	public void init()
	{
		super.init();
		 panel.add(_o, BorderLayout.CENTER); 
	}
	
	@Override
	public void method()
	{
		three();
	}
	
	private void three()
	{
		_o.init();
	}
	
	private void one()
	{
		
		if ((count % 2) == 1)
			_o.stop();
		else
			_o.start();
		

	}
	


}
