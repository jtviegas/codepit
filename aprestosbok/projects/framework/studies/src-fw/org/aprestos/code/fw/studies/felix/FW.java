/**
 * HostApplication.java
 * copyright aprestos.org, 2008.
 */
package org.aprestos.code.fw.studies.felix;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.cache.BundleCache;
import org.apache.felix.framework.util.FelixConstants;
import org.apache.felix.framework.util.StringMap;
import org.apache.felix.main.AutoActivator;

/**
 * 
 */
public class FW
{
    private static final String BUNDLES_EXTENSION = ".jar";
    private static final String CACHE_FOLDER = ".fwcache";
    private static final String BUNDLES_PUB_FOLDER = "bundles/pub";
    private static final String BUNDLES_SYS_FOLDER = "bundles/sys";
	
    private Felix m_felix = null;

    
    public static void main(String[] argv) throws Exception
    {
	
	 try
	        {
	     		FW f = new FW();
	        }
	        catch (Exception ex)
	        {
	            System.err.println("Could not create framework: " + ex);
	            ex.printStackTrace();
	        }
    }
    
    public FW() throws Exception
    {
	
	// Create a temporary bundle cache directory and
	// make sure to clean it up on exit.
	
	
	Runtime.getRuntime().addShutdownHook(new Thread()
	{
		public void run()
		{
			deleteFileOrDir(new File(CACHE_FOLDER));
		}
	});
	
	
        // Create a case-insensitive configuration property map.
        Map configMap = new StringMap(false);
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
        configMap.put(BundleCache.CACHE_PROFILE_DIR_PROP, CACHE_FOLDER);

        
      //check if the bundles' jars do exist
	File pubBundlesFolder = new File(BUNDLES_PUB_FOLDER);
	if (!pubBundlesFolder.exists())
	{
		throw new FileNotFoundException(
				"Could not find the public interfaces bundle!");
	}

	
	File sysBundlesFolder = new File(BUNDLES_SYS_FOLDER);
	if (!sysBundlesFolder.exists())
	{
		throw new FileNotFoundException(
				"Could not find the bundles folder!");
	}
	
	StringBuffer autoActivators = new StringBuffer();
	
	File[] sysBundles = sysBundlesFolder.listFiles(new FileFilter()
	{
		@Override
		public boolean accept(File pathname)
		{
			return pathname.getName().endsWith(BUNDLES_EXTENSION);
		}
	});
	for (File file : sysBundles)
	{
		autoActivators.append("file:" + file.getPath() + " ");
	}
	
	File[] pubBundles = pubBundlesFolder.listFiles(new FileFilter()
	{
		@Override
		public boolean accept(File pathname)
		{
			return pathname.getName().endsWith(BUNDLES_EXTENSION);
		}
	});
	for (File file : pubBundles)
	{
		autoActivators.append("file:" + file.getPath());
	}

	System.out.println("auto activators are " + autoActivators.toString());

	configMap.put(AutoActivator.AUTO_START_PROP + ".1", autoActivators
		.toString().trim());
	configMap.put(FelixConstants.LOG_LEVEL_PROP, "4");
	

            // Create host activator;
            //m_activator = new HostActivator();
            List list = new ArrayList();
            //list.add(m_activator);
            list.add(new AutoActivator(configMap));
            // Now create an instance of the framework with
            // our configuration properties and activator.
            m_felix = new Felix(configMap, list);

            // Now start Felix instance.
            m_felix.start();

    }



    public void shutdownApplication()
    {
        // Shut down the felix framework when stopping the
        // host application.
        m_felix.stopAndWait();
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
