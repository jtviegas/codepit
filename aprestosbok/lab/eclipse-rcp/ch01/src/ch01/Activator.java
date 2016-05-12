package ch01;





import java.util.logging.Logger;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "ch01";

	// The shared instance
	private static Activator plugin;
	private static final Logger logger = Logger.getLogger(Activator.class.getName()); 
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception 
	{
	    	logger.entering(Activator.class.getName(), "start", context);
	    	
	    	logger.info("llllllllllaaaaaaaaaahhhhhhhhhhhhhh");
	    	
		super.start(context);
		plugin = this;
		
		logger.exiting(Activator.class.getName(), "start");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
