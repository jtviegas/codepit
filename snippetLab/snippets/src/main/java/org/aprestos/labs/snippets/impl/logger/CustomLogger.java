package org.aprestos.labs.snippets.impl.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CustomLogger {

	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;

	static public void setup() throws IOException {

		// get the global logger to configure it
		Logger logger = Logger.getLogger("");
		logger.setUseParentHandlers(true);
		fileTxt = new FileHandler("messages.log",2000000,3,false);
		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		logger.addHandler(fileTxt);
		Handler[] handlers = logger.getHandlers();
		for (int index = 0; index < handlers.length; index++) {
			handlers[index].setLevel(Level.FINEST);
		}
		
		logger.setLevel(Level.FINEST);

	}

}
