package org.aprestos.labs.lang.basics;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class Regex {

  @Test
  public void test_00() {
    
    String[] patterns = new String[] {"[a-zA-Z0-9_]{3,10}", "[a-zA-Z0-9_]{3,10}", "[a-zA-Z0-9_]{3,10}","[a-zA-Z0-9_]{3,10}"
        , "[0-9]{9,11}", "[0-9]{9,11}", "[0-9]{9,11}", "[0-9]{9,11}",
        "[0-9]{1,2}-[0-9]{1,7}", "[0-9]{1,2}-[0-9]{1,7}", "[0-9]{1,2}-[0-9]{1,7}", "[0-9]{1,2}-[0-9]{1,7}"
        , "[0-9]{1,2}-[0-9]{1,7}", "[0-9]{1,2}-[0-9]{1,7}", "[0-9]{1,2}-[0-9]{1,7}", "[0-9]{1,2}-[0-9]{1,7}"};
    String[] texts = new String[] {"123", "sab", "_)(*", "adfasf___"
        , "123456789", "1234567895", "12345678956", "12-3456789", 
        "12-3456789", "1234567", "123456789", "12345678956"
        , "1-345679", "12-34567", "1-23456789", "12345678956"
        };
    boolean[] expected = new boolean[] {true,true,false, true, 
        true,true,true,false, 
        true, false, false, false
        , true, true, false, false};
    
    for(int i=0; i< patterns.length && i< texts.length; i++ ) 
      Assert.assertEquals(String.format("pattern: %s | text: %s", patterns[i], texts[i]), expected[i], Pattern.compile(patterns[i]).matcher(texts[i]).matches());

  }
  
  protected String inboundTinTypeETransform(String s) {
    String r = null;
    if( null != s && ( ! s.contains("-") ) && s.length() > 2) {
      r = String.format("%s-%s", s.substring(0, 2), s.substring(2) );
    }
    else
      r = s;
    return r;
  }
  protected String inboundTinTypeSTransform(String s) {
    String r = null;
    if( null != s && ( ! s.contains("-") ) && s.length() > 5 ) {
      r = String.format("%s-%s-%s", s.substring(0, 3), s.substring(3, 5), s.substring(5) );
    }
    else
      r = s;
    return r;
  }
  
  @Test
  public void test_01() {

    String[] input = new String[] {"123456789", "12-3456789"};
    String[] expectedE = new String[] {"12-3456789", "12-3456789" };
    String[] expectedS = new String[] {"123-45-6789", "12-3456789" };

    
    for(int i=0; i< input.length; i++ ) {
      Assert.assertEquals(expectedE[i], inboundTinTypeETransform(input[i]) );
      Assert.assertEquals(expectedS[i], inboundTinTypeSTransform(input[i]) );
    }

  }
 

}
