/*
 * Launcher.java Copyright (C) EID, SA.
 */
package org.aprestos.code.fw;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.cache.BundleCache;
import org.apache.felix.framework.util.FelixConstants;
import org.apache.felix.framework.util.StringMap;
import org.apache.felix.main.AutoActivator;
import org.aprestos.code.fw.exceptions.MissingArgumentException;
import org.aprestos.code.fw.exceptions.MissingPropertyException;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

public class Launcher implements BundleActivator
{
	private static final String BUNDLES_EXTENSION = ".jar";
	private static final String CACHE_FOLDER = ".fwcache";

	/**
	 * Enables the bundle to run as a stand-alone application. When this
	 * static <tt>main()</tt> method is invoked, the application creates
	 * its own embedded Felix framework instance. To successfully
	 * launch as a stand-alone application, this method should be invoked from
	 * the bundle's installation directory using "<tt>java -jar</tt>".
	 * @param argv The command-line arguments.
	 * @throws Exception If anything goes wrong.
	 **/
	public static void main(String[] argv) throws Exception
	{
		
		//expect the firs argument to be the properties file
		String filePath = argv[0];
		if (null == filePath)
			throw new MissingArgumentException("Where is the properties file?");

		//load properties file
		File propertiesFile = new File(filePath);
		Properties properties = new Properties();
		properties.load(new FileInputStream(propertiesFile));

		//it must have the following bundles path:
		//		- public interfaces bundle;
		//		- library bundle path;
		//		- other bundles folder path.
		String pubBundlePath = properties.getProperty(PropertyKey.PUBLIC_INTERFACE_BUNDLE
				.getKey());
		if (null == pubBundlePath || pubBundlePath.length() == 0)
			throw new MissingPropertyException("Must supply "
					+ PropertyKey.PUBLIC_INTERFACE_BUNDLE.getKey() + " property!");
		
		String libBundlePath = properties.getProperty(PropertyKey.LIBRARY_BUNDLE
				.getKey());
		if (null == libBundlePath || libBundlePath.length() == 0)
			throw new MissingPropertyException("Must supply "
					+ PropertyKey.LIBRARY_BUNDLE.getKey() + " property!");
		
		String bundlesDir = properties.getProperty(PropertyKey.BUNDLES_DIR
				.getKey());
		if (null == bundlesDir || bundlesDir.length() == 0)
			throw new MissingPropertyException("Must supply "
					+ PropertyKey.BUNDLES_DIR.getKey() + " property!");

		
		// Create a temporary bundle cache directory and
		// make sure to clean it up on exit.
		final File cachedir = new File(CACHE_FOLDER);
		deleteFileOrDir(cachedir);
		File.createTempFile(CACHE_FOLDER, "");
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				deleteFileOrDir(cachedir);
			}
		});

		//check if the bundles' jars do exist
		File pubBundle = new File(pubBundlePath);
		if (!pubBundle.exists())
		{
			throw new FileNotFoundException(
					"Could not find the public interfaces bundle!");
		}

		File libBundle = new File(libBundlePath);
		if (!libBundle.exists())
		{
			throw new FileNotFoundException(
					"Could not find the library bundle!");
		}
		
		File bundlesFolder = new File(bundlesDir);
		if (!bundlesFolder.exists())
		{
			throw new FileNotFoundException(
					"Could not find the bundles folder!");
		}
		
		StringBuffer autoActivators = new StringBuffer();
		autoActivators.append("file:" + pubBundle.getPath() + " ");
		autoActivators.append("file:" + libBundle.getPath() + " ");
		
		File[] listOfFiles = bundlesFolder.listFiles(new FileFilter()
		{
			@Override
			public boolean accept(File pathname)
			{
				return pathname.getName().endsWith(BUNDLES_EXTENSION);
			}
		});
		
		for (File file : listOfFiles)
		{
			autoActivators.append("file:" + file.getPath() + " ");
		}

		//propperly supply data to the osgi container
		Map configMap = new StringMap(false);
		configMap.put(Constants.FRAMEWORK_SYSTEMPACKAGES,
				"org.osgi.framework; version=1.3.0,"
						+ "org.osgi.service.packageadmin; version=1.2.0,"
						+ "org.osgi.service.startlevel; version=1.0.0,"
						+ "org.osgi.service.url; version=1.0.0");
		
		configMap.put(AutoActivator.AUTO_START_PROP + ".1", autoActivators
				.toString().trim());
		configMap.put(FelixConstants.LOG_LEVEL_PROP, "4");
		configMap.put(BundleCache.CACHE_PROFILE_DIR_PROP, cachedir
				.getAbsolutePath());

		// Create list to hold custom framework activators.
		List list = new ArrayList();
		// Add activator to process auto-start/install properties.
		list.add(new AutoActivator(configMap));
		// Add our own activator.
		list.add(new Launcher());

		// Now create an instance of the framework.
		Felix felix = new Felix(configMap, list);
		felix.start();
		

	}

	/**
	 * Utility method used to delete the profile directory when run as
	 * a stand-alone application.
	 * @param file The file to recursively delete.
	 **/
	private static void deleteFileOrDir(File file)
	{
		if (file.isDirectory())
		{
			File[] childs = file.listFiles();
			for (int i = 0; i < childs.length; i++)
			{
				deleteFileOrDir(childs[i]);
			}
		}
		file.delete();
	}

	@Override
	public void start(BundleContext arg0) throws Exception
	{
		System.out.println("launcher started");
	}

	@Override
	public void stop(BundleContext arg0) throws Exception
	{
		System.out.println("launcher stopped");
	}
}
