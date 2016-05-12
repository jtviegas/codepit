package org.aprestos.labs.snippets.impl.jmx.simple;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class A extends NotificationBroadcasterSupport implements AMBean {

	private final String name = "Bean A";
	private static final int DEFAULT_CACHE_SIZE = 256;
	private int cacheSize = DEFAULT_CACHE_SIZE;
	private long sequenceNumber = 1;
	
	public void doSomething() {}

	public String getName() {
		return name;
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int size) {
		
		int oldSize = this.cacheSize;
		this.cacheSize = size;
		System.out.println("Cache size now " + this.cacheSize);

        Notification n = new AttributeChangeNotification(this,
                                sequenceNumber++, System.currentTimeMillis(),
                                "CacheSize changed", "CacheSize", "int",
                                oldSize, this.cacheSize);

        sendNotification(n);
	}
	
	@Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[]{
            AttributeChangeNotification.ATTRIBUTE_CHANGE
        };

        String name = AttributeChangeNotification.class.getName();
        String description = "An attribute of this MBean has changed";
        MBeanNotificationInfo info = 
                new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[]{info};
    }

}
