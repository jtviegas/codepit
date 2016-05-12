package org.aprestos.code.fw.studies.felix.services.interfaces;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Activator.java
 * copyright aprestos.org, 2008.
 */

/**
 * 
 */
public class Activator implements BundleActivator
{

    /**
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext arg0) throws Exception
    {
	System.out.println("services started");

    }

    /**
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext arg0) throws Exception
    {
	System.out.println("services stopped");

    }

}
