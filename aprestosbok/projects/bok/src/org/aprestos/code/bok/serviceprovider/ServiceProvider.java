/**
 * ServiceProvider.java
 * copyright aprestos.org, 2008.
 */
package org.aprestos.code.bok.serviceprovider;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.cache.BundleCache;
import org.apache.felix.framework.util.FelixConstants;
import org.apache.felix.framework.util.StringMap;
import org.apache.felix.main.AutoActivator;
import org.aprestos.code.bok.common.exceptions.SingletonInitializationException;
import org.osgi.framework.ServiceReference;

/**
 * 
 */
public class ServiceProvider
{
    private static final String BUNDLES_EXTENSION = ".jar";
    
    private static ServiceProvider instance;
    private Felix osgiContainer;
    private ServiceProvider(Properties properties) throws SingletonInitializationException
    {
	init(properties);
    }
    
    public static synchronized void initialize(Properties properties) throws SingletonInitializationException
    {
	if(null == instance)
	{
	    instance = new ServiceProvider(properties);
	}
	
    }
    
    public void shutdown()
    {
	osgiContainer.stopAndWait();
    }
    
    public static synchronized ServiceProvider getInstance()
    {
	return instance;
    }
    
    public Object getService(String name)
    {
	Object result = null;
	
	ServiceReference sr = osgiContainer.getBundleContext().getServiceReference(name);
	if(null != sr)
	    result = osgiContainer.getBundleContext().getService(sr);
	
	return result;
    }
    
    private void init(Properties properties) throws SingletonInitializationException
    {
	try
	{
	
	final String cachedir = new String(properties.getProperty(Keys.BUNDLES_CACHE_DIR.getKey()));
	    
	    
	// Create a case-insensitive configuration property map.
	StringMap configMap = new StringMap(false);
        
        // Configure the Felix instance to be embedded.
        configMap.put(FelixConstants.EMBEDDED_EXECUTION_PROP, "true");
        
        // Add core OSGi packages to be exported from the class path
        // via the system bundle.
        configMap.put(FelixConstants.FRAMEWORK_SYSTEMPACKAGES,
            "org.osgi.framework; version=1.3.0," +
            "org.osgi.service.packageadmin; version=1.2.0," +
            "org.osgi.service.startlevel; version=1.0.0," +
            "org.osgi.service.url; version=1.0.0");
        
        // Explicitly specify the directory to use for caching bundles.
        configMap.put(BundleCache.CACHE_PROFILE_DIR_PROP,cachedir);

        
        Runtime.getRuntime().addShutdownHook(new Thread()
	{
		public void run()
		{
			deleteFileOrDir(new File(cachedir));
		}
	});
        
      //check if the bundles' jars do exist
	File bundlesFolder = new File(Keys.BUNDLES_DIR.getKey());
	if (!bundlesFolder.exists())
	{
		throw new FileNotFoundException(
				"Could not find the bundles directory!");
	}
	
	StringBuffer autoActivators = new StringBuffer();
	
	
	File[] bundles = bundlesFolder.listFiles(new FileFilter()
	{
		@Override
		public boolean accept(File pathname)
		{
			return pathname.getName().endsWith(BUNDLES_EXTENSION);
		}
	});
	for (File file : bundles)
	{
		autoActivators.append("file:" + file.getPath() + " ");
	}

	configMap.put(AutoActivator.AUTO_START_PROP + ".1", autoActivators
		.toString().trim());
	configMap.put(FelixConstants.LOG_LEVEL_PROP, 
		properties.getProperty(Keys.OSGi_LOG_LEVEL.getKey()));
	

            List list = new ArrayList();
            list.add(new AutoActivator(configMap));
            
            // Now create an instance of the framework with
            // our configuration properties and activator.
            osgiContainer = new Felix(configMap, list);

            // Now start Felix instance.
            osgiContainer.start();
            
             
            
	}
	catch(Exception ex)
	{
	    throw new SingletonInitializationException(ex);
	}
    }
    /**
	 * Utility method used to delete the profile directory when run as
	 * a stand-alone application.
	 * @param file The file to recursively delete.
	 **/
	private static void deleteFileOrDir(File f)
	{
		if (f.isDirectory())
		{
			File[] childs = f.listFiles();
			for (int i = 0; i < childs.length; i++)
			{
				deleteFileOrDir(childs[i]);
			}
		}
		f.delete();
	}
    
    
}
