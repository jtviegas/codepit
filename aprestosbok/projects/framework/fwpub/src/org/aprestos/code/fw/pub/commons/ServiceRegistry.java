/*
 * ServiceRegistry.java Copyright (C) EID, SA.
 */
package org.aprestos.code.fw.pub.commons;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.SynchronousBundleListener;


public class ServiceRegistry
{
	
	private final BundleContext context;
	private final SynchronousBundleListener osgiContainerListener;
	private List<BundleListener> bundleListeners = new ArrayList<BundleListener>();
	private List<ServiceListener> serviceListeners = new ArrayList<ServiceListener>();
	private boolean started = false;

	private static ServiceRegistry instance;
	
	public static void initialize(BundleContext context)
	{
		instance = new ServiceRegistry(context);
	}
	
	public static ServiceRegistry getInstance()
	{
		return instance;
	}
	
	/**
	 * @param context The bundle context to use to track bundles.
	 **/
	private ServiceRegistry(BundleContext context)
	{
		this.context = context;
		this.osgiContainerListener = new SynchronousBundleListener()
		{
			
			public void bundleChanged(BundleEvent evt)
			{
				synchronized (ServiceRegistry.this)
				{
					if (!started)
					{
						return;
					}

					if (evt.getType() == BundleEvent.STARTED)
					{
							addedBundle(evt);
					}
					else
						if (evt.getType() == BundleEvent.STOPPED)
						{
								removedBundle(evt);
						}
				}
			}
		};
	}

	/**
	 * @see org.aprestos.code.fw.interfaces.ServiceRegistryInterface#getService(java.lang.String)
	 */
	public synchronized Object getService(String servicename)
	{
		Object result = null;
		
		ServiceReference ref = this.context.getServiceReference(servicename);
		if(null != ref)
			result = this.context.getService(ref);
		
		return result;
	}
	
	/**
	 * @see org.aprestos.code.fw.interfaces.ServiceRegistryInterface#existsService(java.lang.String)
	 */
	public synchronized boolean existsService(String servicename)
	{
		ServiceReference result = null;
		
		result = context.getServiceReference(servicename);
		
		return (null != result);
	}
	
	/**
	 * @see org.aprestos.code.fw.interfaces.ServiceRegistryInterface#existsBundle(java.lang.String)
	 */
	public synchronized boolean existsBundle(String symbolicName)
	{
		boolean result = false;
		
		for(Bundle b:getBundles())
		{
			if(b.getSymbolicName().equals(symbolicName))
			{	
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * @see org.aprestos.code.fw.interfaces.ServiceRegistryInterface#getBundle(java.lang.String)
	 */
	public synchronized Bundle getBundle(String symbolicName)
	{
		Bundle result = null;
		
		for(Bundle b:getBundles())
		{
			if(b.getSymbolicName().equals(symbolicName))
			{	
				result = b;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * @see org.aprestos.code.fw.interfaces.ServiceRegistryInterface#start()
	 */
	public synchronized void start()
	{
		if (!started)
		{
			started = true;

			context.addBundleListener(osgiContainerListener);

			for(Bundle b:getBundles())
			{
				addedBundle(new BundleEvent(BundleEvent.STARTED,b));	
			}

		}
	}

	/**
	 * @see org.aprestos.code.fw.interfaces.ServiceRegistryInterface#stop()
	 */
	public synchronized void stop()
	{
		if (started)
		{
			started = false;

			context.removeBundleListener(osgiContainerListener);
			
			//better off to notify that 
			//bundles will be out of reach
			for(Bundle b:getBundles())
			{
				removedBundle(new BundleEvent(BundleEvent.STOPPED,b));
			}
			
			//clean bundle listeners list
			BundleListener[] bls = bundleListeners.toArray(new BundleListener[bundleListeners.size()]);
			for(BundleListener bl : bls)
			{
				bundleListeners.remove(bl);
			}
			
			//clean service listeners list
			ServiceListener[] sls = serviceListeners.toArray(new ServiceListener[serviceListeners.size()]);
			for(ServiceListener sl : sls)
			{
				serviceListeners.remove(sl);
			}

		}
	}


	
	/**
	 * @see org.aprestos.code.fw.interfaces.ServiceRegistryInterface#addBundleListener(org.osgi.framework.BundleListener)
	 */
	public void addBundleListener(BundleListener l)
	{
		this.bundleListeners.add(l);
	}
	
	/**
	 * @see org.aprestos.code.fw.interfaces.ServiceRegistryInterface#removeBundleListener(org.osgi.framework.BundleListener)
	 */
	public void removeBundleListener(BundleListener l)
	{
		this.bundleListeners.remove(l);
	}
	
	/**
	 * @see org.aprestos.code.fw.interfaces.ServiceRegistryInterface#addServiceListener(org.osgi.framework.ServiceListener)
	 */
	public void addServiceListener(ServiceListener l)
	{
		this.serviceListeners.add(l);
	}
	
	/**
	 * @see org.aprestos.code.fw.interfaces.ServiceRegistryInterface#removeServiceListener(org.osgi.framework.ServiceListener)
	 */
	public void removeServiceListener(ServiceListener l)
	{
		this.serviceListeners.remove(l);
	}
	
//	public synchronized Object getService(ServiceReference serviceref)
//	{
//		Object result = null;
//		
//		result = this.context.getService(serviceref);
//		
//		return result;
//	}
//	
//	public synchronized boolean existsService(ServiceReference serviceref)
//	{
//		Object result = null;
//		
//		result = context.getService(serviceref);
//		
//		return (null != result);
//	}
//	
//	public synchronized List<ServiceReference> getServices()
//	{
//		List<ServiceReference> result = new ArrayList<ServiceReference>();
//		
//		for(Bundle b:getBundles())
//		{
//			for(ServiceReference s:b.getRegisteredServices())
//			{
//				result.add(s);
//			}
//		}
//		return result;
//	}
//	
	/**
	 * Returns the current set of active bundles.
	 * @return The current set of active bundles.
	 **/
	private synchronized List<Bundle> getBundles()
	{
		List<Bundle> result = new ArrayList<Bundle>();
		
		for(Bundle b:context.getBundles())
		{
			if (b.getState() == Bundle.ACTIVE)
				result.add(b);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param bundle The bundle being added to the active set.
	 **/
	private void addedBundle(BundleEvent evt)
	{
		for(BundleListener bl:bundleListeners)
		{
			bl.bundleChanged(evt);
		}
		if(null != evt.getBundle())
		{
			if(null != evt.getBundle().getRegisteredServices())
			{
				for(ServiceReference service : evt.getBundle().getRegisteredServices())
				{
					for(ServiceListener sl:serviceListeners)
					{
						sl.serviceChanged(new ServiceEvent(ServiceEvent.REGISTERED,service));
					}
				}	
			}
				
		}
		
		
	}

	/**
	 * 
	 * @param bundle The bundle being removed from the active set.
	 **/
	private void removedBundle(BundleEvent evt)
	{

		for(ServiceReference service : evt.getBundle().getRegisteredServices())
		{
			for(ServiceListener sl:serviceListeners)
			{
				sl.serviceChanged(new ServiceEvent(ServiceEvent.UNREGISTERING,service));
			}
		}
		
		for(BundleListener bl:bundleListeners)
		{
			bl.bundleChanged(evt);
		}
		
	}

	
}
