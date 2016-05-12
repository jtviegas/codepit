package org.aprestos.code.fw.pub;
import java.util.Hashtable;

import org.aprestos.code.fw.pub.commons.ServiceRegistry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator
{

	@Override
	public void start(BundleContext arg0) throws Exception
	{
		System.out.println("fwpub starting");
		
		ServiceRegistry.initialize(arg0);
		arg0.registerService(ServiceRegistry.class.getName(),
				ServiceRegistry.getInstance(), new Hashtable());
		ServiceRegistry.getInstance().start();
		
		System.out.println("service registry registered");
		System.out.println("fwpub started");
	}

	@Override
	public void stop(BundleContext arg0) throws Exception
	{
		ServiceRegistry.getInstance().stop();
		System.out.println("fwpub stopped");
	}


}