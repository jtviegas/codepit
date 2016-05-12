/*
 * ScaleConnector.java Copyright (C) Wincor Nixdorf.
 */
/**
 * 
 */
package org.aprestos.code.wn.scaleconnector.interfaces;

import org.aprestos.code.wn.scaleconnector.exceptions.ScaleCommunicationException;
import org.aprestos.code.wn.scaleconnector.exceptions.ScaleConnectException;


/**
 * 
 */
public interface ScaleConnectorInterface
{
	void setIp(byte[] ip);
	void setPort(int port);
	void send(String message) throws ScaleCommunicationException;
	void open()throws ScaleConnectException;
	void close() throws ScaleConnectException;
	boolean isOpen();
}
