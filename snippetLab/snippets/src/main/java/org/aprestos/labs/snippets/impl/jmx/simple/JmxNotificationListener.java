package org.aprestos.labs.snippets.impl.jmx.simple;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationListener;

public class JmxNotificationListener implements NotificationListener{


	public void handleNotification(Notification notification, Object handback) {
		
		System.out.println("\nReceived notification:");
		System.out.println("\tClassName: " + notification.getClass().getName());
		System.out.println("\tSource: " + notification.getSource());
		System.out.println("\tType: " + notification.getType());
		System.out.println("\tMessage: " + notification.getMessage());
        if (notification instanceof AttributeChangeNotification) {
            AttributeChangeNotification acn =
                (AttributeChangeNotification) notification;
            System.out.println("\tAttributeName: " + acn.getAttributeName());
            System.out.println("\tAttributeType: " + acn.getAttributeType());
            System.out.println("\tNewValue: " + acn.getNewValue());
            System.out.println("\tOldValue: " + acn.getOldValue());
        }
		
	}

}
