package org.aprestos.labs.snippets.impl.concurrency.filewriting;

import java.util.Random;

public class MessageProducer implements Runnable{

	private Random r;
	private MsgWriter writer;
	
	public MessageProducer(MsgWriter writer) {
		
		r = new Random();
		this.writer = writer;
		
	}
	
	private void start(){
		while(true){
			try {
				Thread.sleep(2000);
				writer.write(Long.toString(r.nextLong()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void run() {
		start();
	}
	

}
