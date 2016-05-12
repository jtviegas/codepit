/*
 * ScaleConnectorFactory.java Copyright (C) Wincor Nixdorf.
 */
/**
 * 
 */
package org.aprestos.code.wn.scaleconnector;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

import org.aprestos.code.logger.LoggerFactory;
import org.aprestos.code.logger.interfaces.LoggerInterface;
import org.aprestos.code.wn.scaleconnector.exceptions.ScaleConnectorException;
import org.aprestos.code.wn.scaleconnector.exceptions.ScaleNotImplementedException;
import org.aprestos.code.wn.scaleconnector.interfaces.ScaleConnectorInterface;


/**
 * 
 */
public class ScaleConnectorFactory
{
	private static LoggerInterface logger = LoggerFactory.getInstance().getLogger(ScaleConnectorFactory.class);
	private static final String PROPS_SUFFIX = ".properties" ;
	private static final String DEFAULT_SCALE_KEY = "default" ;
	private static Properties scales;
	/**
	 * 
	 */
	public static ScaleConnectorInterface getScaleConnector(String scale)
				throws ScaleNotImplementedException, ScaleConnectorException
	{
			ScaleConnectorInterface result = null;
			try
			{
				if(null == scales)
	        	{
	        		String[] classname = ScaleConnectorFactory.class.getName().split("\\.");
	        		logger.debug("going to get input from file " + classname[classname.length-1] + PROPS_SUFFIX);
	      	        InputStream is = ScaleConnectorFactory.class.getResourceAsStream(
	      	        		classname[classname.length-1] + PROPS_SUFFIX);
	      	        scales = new Properties();
	      	        scales.load(is);
	      	        logger.debug("loaded " + classname[classname.length-1] + PROPS_SUFFIX + " file");
	      			is.close();
	      			is = null;
	        	}
	            
				if((null == scale) || (null != scale && 0 == scale.length()))
				{
					scale = DEFAULT_SCALE_KEY;
				}

				String clazz = scales.getProperty(scale);
	        	
	            if(null == clazz)
	            	throw new ScaleNotImplementedException("What scale is this: " + scale + " !!!");
	            
	            Class builderClass = ScaleConnectorFactory.class.getClassLoader()
	    				.loadClass(clazz);
	    		Constructor builderConstructor = builderClass
	    				.getConstructor(new Class[] {});

	    		result = (ScaleConnectorInterface) builderConstructor
	    				.newInstance(new Object[] {});	
			}
			catch(ScaleNotImplementedException snie)
			{
				throw snie;
			}
			catch(Exception x)
			{
				throw new ScaleConnectorException(x);
			}
			
			
			return result;
		}
	
	
	
	
	
	

}
