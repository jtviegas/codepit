package snippetlab.snippets.misc.RegularExpDigressions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jtviegas
 */
public class RegularExpDigressionsTest {

    String[] strings;
    String[] strings2;
    
    public RegularExpDigressionsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() 
    {
        strings = new String[]{"and-sdde-2", "and-2", "-der-1", "asdeerg","00000000000000000000000000000000"};
        strings2 = new String[]{"00000000000000000000000000000000", 
                               "10000000000000000000000000000000", 
                               "11000000000000000000000000000000", 
                               "10100000000000000000000000000000",
                               "10010000000000000000000000000000",
                               "10001000000000000000000000000000",
                               "10000100000000000000000000000000"};
        
    }

    @After
    public void tearDown() 
    {
        
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void findSuffix() 
    {
        
        for(String s : strings)
        {
            Pattern p = Pattern.compile("([0-9].*$");
            Matcher match=p.matcher(s);
            s.substring(match.start(), match.end());
            System.out.println(match.toString());
        }
        for(String s : strings2)
        {
            Pattern p = Pattern.compile("([0-9].*$");
            Matcher match=p.matcher(s);
//            match.
            s.substring(match.start(), match.end());
            System.out.println(match.toString());
        }
        
    }

}