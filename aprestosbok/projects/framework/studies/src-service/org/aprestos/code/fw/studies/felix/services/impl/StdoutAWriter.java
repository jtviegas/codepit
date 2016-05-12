/**
 * StdoutAWriter.java
 * copyright aprestos.org, 2008.
 */
package org.aprestos.code.fw.studies.felix.services.impl;

import org.aprestos.code.fw.studies.felix.services.interfaces.StdoutWriter;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * 
 */
public class StdoutAWriter implements StdoutWriter,BundleActivator
{

    /**
     * @see org.aprestos.code.fw.studies.felix.services.interfaces.StdoutWriter#write(java.lang.String)
     */
    @Override
    public void write()
    {
	System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    }

    /**
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext arg0) throws Exception
    {
	System.out.println("StdoutAWriter starting");
    }

    /**
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext arg0) throws Exception
    {
	System.out.println("StdoutAWriter stopping");
    }

}
