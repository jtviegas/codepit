package org.aprestos.labs.snippets.impl.io.gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

public class GsonTest {

    private static final String JSON_FILE = "tmp/gson.json";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * loop through one interval generating messages and wait for the file to be
     * closed and check the number of files and number of lines in files
     * 
     * @throws IOException
     */
    @Test
    public void readIt() throws IOException {

	Gson gson = new Gson();
	try (Reader reader = new FileReader(JSON_FILE)) {

	    // Convert JSON to Java Object
	    //FEConf staff = gson.fromJson(reader, FEConf.class);
	    @SuppressWarnings("unchecked")
	    Map<String, Map<String, String>> staff  = (Map<String, Map<String, String>>) gson.fromJson(reader,  Map.class);
	    
	    System.out.println(staff.get("cron_2").get("warmingFactorInDays"));
	    System.out.println(staff.get("cron_1").get("warmingFactorInDays"));
	    System.out.println(staff);

	    // Convert JSON to JsonElement, and later to String
	    /*
	     * JsonElement json = gson.fromJson(reader, JsonElement.class);
	     * String jsonInString = gson.toJson(json);
	     * System.out.println(jsonInString);
	     */
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }
    
    
    public class FEConf {
	EConf[] cron;
	EConf tod;
    }
    
    public class EConf {
	public String expression, strictlyOnWeekdayStrategy, recurrenceMsg;
	public int warmingFactorInDays;
    }
    
}
