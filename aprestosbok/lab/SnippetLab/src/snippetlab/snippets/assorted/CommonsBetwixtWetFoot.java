/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.beans.IntrospectionException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.xml.sax.SAXException;
import snippetlab.interfaces.SnippetInterface;
import snippetlab.snippets.misc.commonsBetwixtWetFoot.Entities;
import snippetlab.snippets.misc.commonsBetwixtWetFoot.Role;

/**
 *
 * @author jtviegas
 */
public class CommonsBetwixtWetFoot implements SnippetInterface
{
    private static final String FILE="conf/entitiestest.xml";
    
    public void go(JPanel panel, JFrame frame) 
    {
        
//       testBetwixt();
//       int a = amplitude(new int[]{23,34,56,3,7,89,22});
//       int b = absDistinct(new int[]{23,-23,-34,34,2,56,56,78,-7,7,3,89,22});

       //int c = complementary_pairs(6, new int[]{2,4,1,5,-2,8,56,-50,-7,3,3,89,22});
//    	int c = complementary_pairs(6, new int[]{1,0,-2,8,1,4,-3,3,5});
    	test_a();
    }

    
    double test_a()
    {
    	int[] a= new int[3];
    	
    	
    	double x = 7/4;
    	
    	A aa = new B(2);
    	aa.doit();
    	System.out.println(aa.l);
    	
    	C c = new C(2);
    	System.out.println(c.l);
    	
    	JComponent _f = new JButton();
    	
    	byte b1 = 2;
    	byte b2 = 0;
    	//byte b3 = b1 + b2;
    	
    	int n=2;
    	int z=n/0;
    	System.out.println(z);
    	
    	return x;
    }
    
    class A
    {
    	int l;
    	void doit()
    	{
    		System.out.println("a");
    	}
    	A()
    	{
    		this.l += 3;
    	}
    
    }
    
    class B extends A
    {
    	void doit()
    	{
    		System.out.println("b");
    	}
    	B()
    	{
    		this.l += 4;
    		
    	}
    	B(int k)
    	{
    		this.l += k;
    		
    	}
    }
    
    class C extends B
    {
    	void doit()
    	{
    		System.out.println("b");
    	}
    	C()
    	{
    		this.l += 4;
    		
    	}
    	C(int k)
    	{
    		this.l += k;
    		
    	}
    }
    
    int complementary_pairs ( int k,int[] A ) 
    {
    	int _result = 0;
    	
    	int a = 0;
        int b = 0;
        
      for(int i=0 ; i < A.length ; i++)
      {
        a = A[i];
        for(int j = A.length - 1 ; j >= i ; j--)
        {
            b = A[j];
            
           if(k == (a + b))
           {
        	   if(j==i)
        		   _result++;
        	   else
        		   _result+=2;
           }
        }
      }
    	
    	return _result;
    }
    
    int absDistinct ( int[] A ) 
    {
        // write your code here
//    	that, given a non-empty zero-indexed array A consisting of N numbers, returns absolute distinct count of array A.
//    	Assume that:
//    	array A is sorted;
//    	N is within the range [1..100,000];
//    	each element of array A is an integer within the range [-2,147,483,648..2,147,483,647].
    	
    	int _current = 0, _found = 0;
    	int[] _absolutes = getAbsoluteValues(A);
    	Arrays.sort(_absolutes);
    	
    	_current = _absolutes[0];
    	_found++;
    	
    	for(int i=1 ; i < _absolutes.length ; i++)
        {
    		if (_absolutes[i] != _current)
    		{
    			_current = _absolutes[i]; 
    			_found++;
    		}
        }
    	
    	return _found;
    }
    
    int[] getAbsoluteValues(int[] _x)
    {
    	int[] _result = new int[_x.length];
    	
    	for(int i=0 ; i < (_x.length) ; i++)
        {
    		 _result[i]= Math.abs(_x[i]);
        }
    	
    	return _result;
    }
    
    int amplitude ( int[] A ) 
    {
        int a = 0;
        int b = 0;
        int current_amp = 0;
        int max_amp = 0;
        
      for(int i=0 ; i < (A.length - 1) ; i++)
      {
        a = A[i];
        for(int j = A.length - 1 ; j > i ; j--)
        {
            b = A[j];
            current_amp = Math.abs(a-b);
            if(current_amp > max_amp)
                max_amp = current_amp;
        }
      }
      
      return max_amp;
    }
    
    public void testBetwixt()
    {
        
        try
        {
            String entitiesFile = FILE;
			
            File file = new File(entitiesFile);
            if(file.exists())
                file.delete();
            
            
            Entities entitiesA = readObj();
            printObjs(entitiesA);
            
            entitiesA.addRole(new Role("ze"));
            entitiesA.addRole(new Role("za"));
            
            writeObj(entitiesA.getRoles());
            
            Entities entitiesB = readObj();
            printObjs(entitiesB);
            
            entitiesB.removeRole(new Role("ze"));
            
            writeObj(entitiesB.getRoles());
            
            Entities entitiesC = readObj();
            printObjs(entitiesC);
            
            entitiesC.addRole(new Role("to"));
            entitiesC.addRole(new Role("ta"));

            writeObj(entitiesC.getRoles());
            
            Entities entitiesD = readObj();
            printObjs(entitiesD);
            
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    
    public void writeObj(List<Role> roles) throws Exception
    {
        String entitiesFile = FILE;

			
	Entities entities = new Entities();
			
	File file = new File(entitiesFile);
	if(!file.exists())
	{
		write(entities, entitiesFile);
	}
        
        entities.setList(roles);
        write(entities, entitiesFile);
        
    }
    
    public Entities readObj()throws Exception
    {
        Entities entities= new Entities();
			
	String entitiesFile = FILE;
			
	File file = new File(entitiesFile);
	
        if(file.exists())
	{
            BufferedReader reader =
                new BufferedReader(new InputStreamReader(
                    new FileInputStream(entitiesFile)	
                    )
		);

            BeanReader beanReader  = new BeanReader();
		       beanReader.getXMLIntrospector()
            	       	.getConfiguration().setAttributesForPrimitives(
		       		false);
            beanReader.getBindingConfiguration().setMapIDs(false);
		    
	    beanReader.registerBeanClass("class-entities", Entities.class);
		        
		        // Now we parse the xml
            entities = (Entities) beanReader.parse(reader);
	}
	else
            System.out.println("entities file was not found.");
            
        return entities;
        
    }
    private void write(Entities entities, String file) throws UnsupportedEncodingException, 
	FileNotFoundException, SAXException, IntrospectionException , IOException
	{

		BufferedWriter out = 
			new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(file)
							)
					);
		
		BeanWriter writer = new BeanWriter(out);

	    writer.getXMLIntrospector().getConfiguration()
	            .setAttributesForPrimitives (true);

	    writer.enablePrettyPrint();
	    writer.getBindingConfiguration().setMapIDs(false);
	    writer.write(entities);  
	    writer.close();
	}
    
    

    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    
//    public static void writeEntities(Entities obj)
//    {
//        try
//        {
//        // create write and set basic properties
//       // BeanWriter writer = new BeanWriter(new FileWriter("conf/bundles.xml"));
//        
//        BufferedWriter out = 
//		new BufferedWriter(
//                    new OutputStreamWriter(
//			new FileOutputStream("conf/bundle.xml"),"UTF-16"
//					)
//				);
//			
//	BeanWriter writer = new BeanWriter(out); 
//        
//        writer.getXMLIntrospector().getConfiguration()
//            .setAttributesForPrimitives (true);
//
//        writer.enablePrettyPrint();
//        writer.getBindingConfiguration().setMapIDs(false);
//
//        
//        
//            // write out the bean
//            writer.write(obj);  
//            
//            writer.close();
//        }
//        catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
//    }
//    
//    
//    public static Entities readEntities()
//    {
//        Entities result=null;
//        try
//        {
//            File file = new File("conf/bundle.xml");
//            if(!file.exists())
//                writeEmptyFile();
//            
//            //FileReader reader = new FileReader("conf/bundle.xml");
//            BufferedReader reader =
//				new BufferedReader(new InputStreamReader(
//								new FileInputStream("conf/bundle.xml"), "UTF-16"	
//								)
//					);
//            // Now convert this to a bean using betwixt
//            // Create BeanReader
//            BeanReader beanReader  = new BeanReader();
//        
//            // Configure the reader
//            // If you're round-tripping, make sure that the configurations are compatible!
//            beanReader.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
//            beanReader.getBindingConfiguration().setMapIDs(false);
//        
//            // Register beans so that betwixt knows what the xml is to be converted to
//            // Since the element mapped to a PersonBean isn't called the same 
//            // as Betwixt would have guessed, need to register the path as well
//            beanReader.registerBeanClass("entities",Entities.class);
//            //beanReader.registerBeanClass("role",snippetlab.snippets.misc.commonsBetwixtWetFoot.Role.class);
//
//            // Now we parse the xml
//            result = (Entities) beanReader.parse(reader);
//            
//            System.out.println(result.toString());
//        }
//        catch(Exception ex)
//        {
//           ex.printStackTrace(); 
//        }
//        return result;
//        
//    }
//    
//    
//    
//    private static void writeEmptyFile()
//    {
//        Entities o=new Entities();
//        writeEntities(o);
//    }
//    
    private static void printObjs(Entities e)
    {
        System.out.println("~~~~~~~~~~");
        
        for (Role role:e.getRoles())
            System.out.println("role -> " + role.getName());
    }
//
//    
    
}
