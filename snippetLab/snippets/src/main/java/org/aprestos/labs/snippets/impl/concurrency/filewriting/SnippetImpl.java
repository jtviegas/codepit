package org.aprestos.labs.snippets.impl.concurrency.filewriting;

import org.springframework.stereotype.Component;



public class SnippetImpl implements org.aprestos.labs.snippets.interfaces.Snippet 
{
	public SnippetImpl(){}
	
	public void go() throws Exception {
		
		MsgWriter writer = new MsgWriterImpl();
		
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		new Thread(new MessageProducer(writer)).start();
		
	}
	
}
