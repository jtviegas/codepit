/*
 * DomParser.java Copyright (C) Wincor Nixdorf.
 */
package snippetlab.snippets.xml.domparser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import snippetlab.snippets.AbstractSnippet;

public class DomParser extends AbstractSnippet
{
	
	@Override
	public void method()
	{
		Element node = null;
		Element nodeclone = null;
		Document newDocFromNode = null;
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		
		try
		{
			String root="project";

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document file = builder.parse("xml.xml");
			
			Document document = builder.newDocument();
			
			Element newroot = document.createElement(root);
			document.appendChild(newroot);
			
			Element fileroot = file.getDocumentElement();
			NodeList filenodes = fileroot.getElementsByTagName("*");;
			
			
			for(int i = 0 ; i < filenodes.getLength() ; i++ )
			{
				
		        node = (Element)filenodes.item(i);

		        Source src = new DOMSource(node);
		        Result dest = new StreamResult(bytes);
		        Transformer transformer = TransformerFactory.newInstance().newTransformer(); 
		        transformer.transform(src, dest);

		        System.out.println(bytes.toString());
		        //nodeclone = (Element) node.cloneNode(true);

		        				
			}
			
			
			
			
		}
		catch(Exception x)
		{
			x.printStackTrace();
			System.out.println(x.getMessage());
		}
				
	

	}

}
