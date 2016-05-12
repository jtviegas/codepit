/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.aprestos.code.bok.rdbms;

import java.util.Properties;

import org.aprestos.code.bok.logger.LoggerFactory;
import org.aprestos.code.bok.logger.interfaces.LoggerInterface;
import org.aprestos.code.bok.rdbms.exceptions.RDBMSInitializeException;
import org.aprestos.code.bok.rdbms.factories.DerbyEmbeddedRDBMSFactory;
import org.aprestos.code.bok.rdbms.interfaces.RDBMSFactoryInterface;
import org.aprestos.code.bok.rdbms.interfaces.RDBMSInterface;
import org.aprestos.code.bok.rdbms.utils.Driver;
import org.aprestos.code.bok.rdbms.utils.Property;


/**
 * Abstract Factory for a relational database management system(RDBMS).
 * Provides  factories for the supported RDBMS's interface.
 *
 * @author jmv
 */
public abstract class AbstractRDBMSFactory implements RDBMSFactoryInterface
{

    private static LoggerInterface logger = LoggerFactory.getInstance().getLogger(AbstractRDBMSFactory.class);
    
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
    public static AbstractRDBMSFactory getFactory(Properties properties)
    {
    	logger.enter("getFactory", new Object[]{properties});
	
    	AbstractRDBMSFactory result = null;
        
        String driver = properties.getProperty(Property.driver.getKey());
        
        if(driver.equals(Driver.derby_embedded.getName()))
        {
            result = new DerbyEmbeddedRDBMSFactory(properties);
        }
        
        //....we can still accommodate 
        //here further driver rdbms factories
        
        logger.leave("getFactory", result);
        
        return result;
    }
    
    /**
     * Initializes the RDBMS, possibly creating the appropriate files if needed.
     * 
     * @see org.aprestos.code.bok.rdbms.interfaces.RDBMSFactoryInterface#initialize()
     */
    public abstract void initialize() throws RDBMSInitializeException;
    
    /**
     * Get the RDBMS implementation object.
     * 
     * @see org.aprestos.code.bok.rdbms.interfaces.RDBMSFactoryInterface#getRDBMS()
     */
    public abstract RDBMSInterface getRDBMS();
    
}
