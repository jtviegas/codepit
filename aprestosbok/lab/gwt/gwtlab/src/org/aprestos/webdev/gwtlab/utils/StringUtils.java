/*
 * StringUtils.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package org.aprestos.webdev.gwtlab.utils;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * 
 */
public class StringUtils
{

    public static String escapeHtml(String maybeHtml)
    {
	final Element div = DOM.createDiv();
	DOM.setInnerText(div, maybeHtml);
	return DOM.getInnerHTML(div);
    }
    
}
