/**
 * Boot.java
 * copyright aprestos.org, 2010.
 */
package org.aprestos.code.labs.jasf;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.aprestos.code.labs.jasf.misc.Constants;
import org.springframework.richclient.application.ApplicationLauncher;

import com.wincor_nixdorf.pt.labs.commons.exceptions.FactoryInitializeException;
import com.wincor_nixdorf.pt.labs.commons.logger.LoggerFactory;
import com.wincor_nixdorf.pt.labs.commons.logger.interfaces.LoggerInterface;

/**
 * 
 */
public class Boot
{
    private static LoggerInterface logger;
    
    public void go()
    {
	try
	{
	    InputStream _is = new FileInputStream(Constants.APP_NAME + Constants.PROPERTIES_SUFFIX);
	    Properties _props = new Properties();
	    _props.load(_is);
	    _is.close();
	    _is = null;
	    LoggerFactory.initialize(_props);
	    logger = LoggerFactory.getInstance().getLogger(Boot.class);
	    logger.enter("go");
	    
	    ;
	    String _startupContext = "/" + this.getClass().getPackage().getName().replaceAll("\\.", "/") 
	    				+ "/" + Constants.STARTUP_CONTEXT_FILE;
	    String _appContext = "/" + this.getClass().getPackage().getName().replaceAll("\\.", "/") 
	    				+ "/" + Constants.APP_CONTEXT_FILE;
	    
	    new ApplicationLauncher(_startupContext, new String[]{_appContext});
	    
	    logger.leave("go");
	    
	} 
	catch (FileNotFoundException e)
	{
	    logger.error("!!! FileNotFoundException during boot !!!", e);
	} 
	catch (IOException e)
	{
	    logger.error("!!! IOException during boot !!!", e);
	} 
	catch (FactoryInitializeException e)
	{
	    logger.error("!!! FactoryInitializeException during boot !!!", e);
	}
	catch(RuntimeException re)
	{
	    logger.error("!!! Runtime Exception during boot !!!", re);
	}
    }

}
