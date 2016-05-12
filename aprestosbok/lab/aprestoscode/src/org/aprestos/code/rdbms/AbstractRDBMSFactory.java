/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.aprestos.code.rdbms;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

import org.aprestos.code.logger.LoggerFactory;
import org.aprestos.code.logger.interfaces.LoggerInterface;
import org.aprestos.code.rdbms.exceptions.RDBMSException;
import org.aprestos.code.rdbms.exceptions.RDBMSInitializeException;
import org.aprestos.code.rdbms.exceptions.RDBMSNotImplementedException;
import org.aprestos.code.rdbms.interfaces.RDBMSFactoryInterface;
import org.aprestos.code.rdbms.interfaces.RDBMSInterface;
import org.aprestos.code.rdbms.utils.Property;



/**
 * Abstract Factory for a relational database management system(RDBMS).
 * Provides  factories for the supported RDBMS's interface.
 *
 * @author jmv
 */
public abstract class AbstractRDBMSFactory implements RDBMSFactoryInterface
{

    private static LoggerInterface logger = LoggerFactory.getInstance().getLogger(AbstractRDBMSFactory.class);
    private static Properties classes;
    private static final String PROPS_SUFFIX = ".properties" ;
    
  
    /**
     * Resolves the appropriate RDBMS factory object
     * based on the properties supplied. 
     * There should be an <b>aprestos</b> logger already initialized also.
     * 
     * The properties file supplied must comply with
     * the following structure:
     * <pre>
     * # ------------beginning of properties-------------------
     * 
     * # --- common ---
     * db.name=mydb
     * user=user
     * password=pass
     * driver=org.apache.derby.jdbc.EmbeddedDriver
     * protocol=jdbc:derby:
     * db.schema=/snippetlab/snippets/misc/DerbyDigressions/rdbms/conf/rdbms.xml
     * 
     * # --- derby embedded specific props ---
     * derby.embedded.rdbms.folder=.derby
     * derby.embedded.vars.create=true
     * derby.embedded.vars.shutdown=true
     * 
     * #-------------end of properties-------------
     * </pre>
     * @param properties
     * @return static instance of the factory
     */
    public static RDBMSFactoryInterface getFactory(Properties properties) throws RDBMSNotImplementedException, RDBMSException
    {
    	logger.enter("getFactory", new Object[]{properties});
    	RDBMSFactoryInterface result = null;

    	try
        {
        	if(null == classes)
        	{
        		//get the properties file named after this class
        		String[] classname = AbstractRDBMSFactory.class.getName().split("\\.");
        		logger.debug("going to get input from file " + classname[classname.length-1] + PROPS_SUFFIX);
      	        InputStream is = AbstractRDBMSFactory.class.getResourceAsStream(classname[classname.length-1] + PROPS_SUFFIX);
      	        classes = new Properties();
      	        classes.load(is);
      	        logger.debug("loaded " + classname[classname.length-1] + PROPS_SUFFIX + " file");
      			is.close();
      			is=null;
        	}
            //the client properties must tell us what type of rdbms is wanted
        	String rdbms = properties.getProperty(Property.rdbms_name);
        	//this properties file is going to provide us with the implementer class
        	String clazz = classes.getProperty(rdbms);
        	
            if(null == clazz)
            	throw new RDBMSNotImplementedException(rdbms);
            
            Class builderClass = AbstractRDBMSFactory.class.getClassLoader()
    				.loadClass(clazz);
    		Constructor builderConstructor = builderClass
    				.getConstructor(new Class[] {Properties.class});

    		result = (RDBMSFactoryInterface) builderConstructor
    				.newInstance(new Object[] {properties});
            
        }
        catch(RDBMSNotImplementedException nie)
        {
        	throw nie;
        }
        catch (Exception e) 
        {
			throw new RDBMSException(e);
		}
    	
        logger.leave("getFactory", result);
        return result;
    }
    
    /**
     * Initializes the RDBMS, possibly creating the appropriate files if needed.
     * 
     * @see org.aprestos.code.rdbms.interfaces.RDBMSFactoryInterface#initialize()
     */
    public abstract void initialize() throws RDBMSInitializeException;
    
    /**
     * Get the RDBMS implementation object.
     * 
     * @see org.aprestos.code.rdbms.interfaces.RDBMSFactoryInterface#getRDBMS()
     */
    public abstract RDBMSInterface getRDBMS();
    
}
