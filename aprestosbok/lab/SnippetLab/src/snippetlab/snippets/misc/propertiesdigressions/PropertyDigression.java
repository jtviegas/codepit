/*
 * PropertyDigression.java
 */
/**
 * 
 */
package snippetlab.snippets.misc.propertiesdigressions;

import java.io.InputStream;
import java.util.Properties;

import snippetlab.snippets.AbstractSnippet;

/**
 * 
 */
public class PropertyDigression extends AbstractSnippet
{
//    snippetlab.snippets.misc.propertiesdigressions.PropertyDigression
    /**
     * 
     */
    public PropertyDigression()
    {
	// TODO Auto-generated constructor stub
    }

    /**
     * @see snippetlab.snippets.AbstractSnippet#method()
     */
    @Override
    public void method()
    {
	try
	{
	    InputStream is = PropertyDigression.class.getResourceAsStream("props");
	    Properties p = new Properties();
	    p.load(is);
	    System.out.println(p.getProperty("key.1"));
	    System.out.println(p.getProperty("key.2"));
	    System.out.println(p.getProperty("key.3"));
	}
	catch(Exception x)
	{
	    x.printStackTrace();
	}
	
	
    }

}
