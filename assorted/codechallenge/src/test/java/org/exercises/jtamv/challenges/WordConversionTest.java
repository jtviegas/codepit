package org.exercises.jtamv.challenges;

import org.exercises.jtamv.challenges.WordConversion;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static java.lang.String.format;

public class WordConversionTest {

    private static WordConversion wordConversion;
    
    @BeforeClass
    public static void setUpTestObj() throws Exception {
	wordConversion = new WordConversion();
    }

    @Test
    public void testOtherExamples() {
	int[] ints = {1290347 
			, 1209347 
			, 1209000
			, 1200001
			, 1000000
			, 1000
			, 100
			, 10
			, 100000000
			, 100000001
			, 100001000
			, 100000100
			, 100000101
			, 100001101
		};
	String[] expected = {"one million two hundred and ninety thousand three hundred and forty seven", 
		"one million two hundred and nine thousand three hundred and forty seven"
		, "one million two hundred and nine thousand"
		, "one million two hundred thousand and one"
		, "one million"
		, "one thousand"
		, "one hundred"
		, "ten"
		, "one hundred million"
		, "one hundred million and one"
		, "one hundred million and one thousand"
		, "one hundred million one hundred"
		, "one hundred million one hundred and one"
		, "one hundred million and one thousand one hundred and one"
	};
	for(int i = 0; i < ints.length; i++)
	    Assert.assertEquals(format("failed %d", ints[i]), expected[i], wordConversion.int2String(ints[i]));

    }
    
    @Test
    public void testProvidedExamples() {
	int[] ints = {0, 1, 21, 105, 123, 
			1005, 1042, 1105, 
			56945781, 
			999999999};
	String[] expected = {"zero", "one","twenty one" ,"one hundred and five" ,"one hundred and twenty three", 
			"one thousand and five" ,"one thousand and forty two" ,"one thousand one hundred and five"
		    ,"fifty six million nine hundred and forty five thousand seven hundred and eighty one"
		    ,"nine hundred and ninety nine million nine hundred and ninety nine thousand nine hundred and ninety nine"};
	for(int i = 0; i < ints.length; i++)
	    Assert.assertEquals(format("failed %d", ints[i]), expected[i], wordConversion.int2String(ints[i]));

    }
    
    //test out of bounds int
    @Test(expected=IllegalArgumentException.class)
    public void testHighInt() {
	wordConversion.int2String(1000000000);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testLowInt() {
	wordConversion.int2String(-1);
    }
    
    @Test
    public void testDegreeFactorTranslation() {
	int[] ints = {0, 1, 21, 105, 123, 100, 10, 11, 20, 999};
	String[] expected = {"zero", "one","twenty one"
		    ,"one hundred and five"
		    ,"one hundred and twenty three", "one hundred", "ten", "eleven", "twenty", "nine hundred and ninety nine"};
	for(int i = 0; i < ints.length; i++)
	    Assert.assertEquals(format("failed %d", ints[i]), expected[i], wordConversion.factorNumberTranslation(ints[i], true));

    }
    

}

