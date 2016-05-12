package com.williamhill.logging;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging 
{
	
	public static void setup() throws SecurityException, IOException
	{
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		
		ConsoleHandler _handler = new ConsoleHandler();
		_handler.setFormatter(new SimpleFormatter());
		logger.addHandler(_handler);
		logger.setLevel(Level.CONFIG);
	}
	

}
