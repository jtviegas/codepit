package snippetlab.snippets.tests;

public class JTest 
{ 
    private String mString1 = "str1"; 
    private String mString2; 
    private static String mSTRING = "str2"; 
    private String iString; 
    private static String sString; 
     
    public JTest () 
    { 
         mString2 = "str5"; 
    } 
     
    { 
        iString = "str3"; 
        System.out.println(mString1); 
        System.out.println(mString2); 
        System.out.println(iString); 
    } 
     
    static 
    { 
        sString = "str4"; 
        System.out.println(mSTRING); 
        System.out.println(sString); 
    } 
     
  
}
