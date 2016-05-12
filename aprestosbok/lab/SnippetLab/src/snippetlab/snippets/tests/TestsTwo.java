package snippetlab.snippets.tests;

import java.io.File;

import snippetlab.snippets.AbstractSnippet;

//snippetlab.snippets.tests.TestsTwo

public class TestsTwo extends AbstractSnippet
{

	public TestsTwo()
	{
		
	}

	@Override
	public void method()
	{
		System.out.println("	@TestsTwo::method");
		
		int a = 7; 
		double b = a / 2; 
		System.out.println("b = " + b);
	
		System.out.println("	TestsTwo::method@");
		
//		HashMap map = new HashMap(); 
//	     
//		map.put("list", new ArrayList()); 
//		map.put("map", new HashMap()); 
//		map.put("list", new LinkedList()); 
//		map.put("table", new Hashtable()); 
//		     
//		Iterator iterator = map.keySet().iterator(); 
//		while (iterator.hasNext()) 
//		{ 
//		    String key = (String)iterator.next(); 
//		    Collection collection = (Collection)map.get(key); 
//		    System.out.println(key + " " + collection.size()); 
//		}
		
		AnInterface p = new Class2();
		AnInterface p2 = new Class4();
		try
		{
			p.someMethod("");
			p2.someMethod("");
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 String string3 = new String("string").intern();
		 
		 JTest jTest = new JTest();
		 File f = new File("myfile.txt");
		 
	}
	
	interface AnInterface 
	{ 
	    void someMethod(String someParam) throws Exception; 
	}
	
	public class Class2 implements AnInterface 
	{ 
	    public void someMethod(String someParam) {}; 
	}
	    
	public abstract class Class3 implements AnInterface 
	{ 
	    public abstract void someMethod(String someParam) throws Exception; 
	}
	    
	public class Class4 extends Class3 
	{ 
	    public void someMethod(String someParam) {}; 
	}
	
}
