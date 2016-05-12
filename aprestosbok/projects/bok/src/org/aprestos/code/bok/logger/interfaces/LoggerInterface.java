package org.aprestos.code.bok.logger.interfaces;

public interface LoggerInterface {
 
	public  void enter(String method, Object[] p);
	public  void leave(String method, Object result);
	public  void enter(String method);
	public  void leave(String method);
	public  void enterIn(String method, Object[] p);
	public  void leaveIn(String method, Object result);
	public  void enterIn(String method);
	public  void leaveIn(String method);
	public  void debug(String message);
	public  void debug(String message, Throwable t);
	public  void info(String message, Throwable t);
	public  void info(String message);
	public  void warn(String message, Throwable t);
	public  void warn(String message);
	public  void error(String message, Throwable t);
	public  void error(String message);
	public  void error(Throwable t);
	public  void fatal(String message, Throwable t);
	public  void fatal(String message);
	public  void fatal(Throwable t);

}
 
