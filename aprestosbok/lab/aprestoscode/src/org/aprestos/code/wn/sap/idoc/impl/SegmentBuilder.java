/*
 * TokenizerBuilder.java Copyright (C) Wincor Nixdorf.
 */
package org.aprestos.code.wn.sap.idoc.impl;


import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.aprestos.code.wn.sap.idoc.interfaces.ISegment;
import org.xml.sax.SAXException;

public class SegmentBuilder
{
	private Segment segment;
	
	public ISegment build(String file) throws IOException, SAXException
	{
		segment = new Segment();
		Digester digester = new Digester();
		digester.push(this);
		
		digester.addCallMethod("Segment/Fields/Field","addField", 4);
		digester.addCallParam("Segment/Fields/Field/startIndex", 0);
		digester.addCallParam("Segment/Fields/Field/endIndex", 1);
		digester.addCallParam("Segment/Fields/Field/name", 2);
		digester.addCallParam("Segment/Fields/Field/value", 3);

		digester.parse(SegmentBuilder.class.getResourceAsStream(file));
		
		
		return (ISegment)segment;
	}
	
	public void addField(String startIndex, String endIndex, String name,String value)
	{
		segment.fields.add(new Field(startIndex,endIndex,name,value));
	}

}
