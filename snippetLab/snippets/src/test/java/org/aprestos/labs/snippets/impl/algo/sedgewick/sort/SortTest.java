package org.aprestos.labs.snippets.impl.algo.sedgewick.sort;

import java.util.Arrays;

import org.apache.commons.lang.time.StopWatch;
import org.aprestos.labs.snippets.impl.algo.sedgewick.Generator;
import org.aprestos.labs.snippets.impl.algo.sedgewick.sort.AbstractSorter.SorterType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SortTest {

    private static final int N = 9 * 1000;
    
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testInt() {
	Integer[] generatedValues = Generator.generate(N, Integer.class);
	
	for(SorterType st: SorterType.values()){
	    Integer[] values = Arrays.copyOf(generatedValues, generatedValues.length);
	    StopWatch watch = new StopWatch();
	    Sorter<Integer> sorter = AbstractSorter.getInstance(st, values);
	    watch.start();
	    sorter.sort();
	    watch.stop();
	    System.out.println(st.name()  +"<Integer>: " + watch.getTime());
	    System.out.println(Arrays.deepToString(Arrays.copyOf(values, 16)));
	    Assert.assertTrue(AbstractSorter.isSorted(values, 0, values.length-1));
	}
	
    }
    
    @Test
    public void testDbl() {
	
	Double[] generatedValues = Generator.generate(N, Double.class);
	//Double[] generatedValues = {0.8731339946702313d, 0.771977712246956d, 0.8219836551818556d, 0.033345006166979774d, 0.42112037305101524d, 0.8782967728449862d};
	for(SorterType st: SorterType.values()){
	    Double[] values = Arrays.copyOf(generatedValues, generatedValues.length);
	    StopWatch watch = new StopWatch();
	    Sorter<Double> sorter = AbstractSorter.getInstance(st, values);
	    watch.start();
	    sorter.sort();
	    watch.stop();
	    System.out.println(st.name()  +"<Double>: " + watch.getTime());
	    System.out.println(Arrays.deepToString(Arrays.copyOf(values, 16)));
	    Assert.assertTrue(AbstractSorter.isSorted(values, 0, values.length-1));
	}
    }
    
    @Test
    public void testStr() {
	int n = N;
	String[] generatedValues = Generator.generate((n < 100 ? n : n/100), String.class);
	for(SorterType st: SorterType.values()){
	    String[] values = Arrays.copyOf(generatedValues, generatedValues.length);
	    StopWatch watch = new StopWatch();
	    Sorter<String> sorter = AbstractSorter.getInstance(st, values);
	    watch.start();
	    sorter.sort();
	    watch.stop();
	    System.out.println(st.name()  +"<String>: " + watch.getTime());
	    System.out.println(Arrays.deepToString(Arrays.copyOf(values, 16)));
	    Assert.assertTrue(AbstractSorter.isSorted(values, 0, values.length-1));
	}
    }
    
/*    @Test
    public void testQuick2() {
	String[] items = "P A B X W P P V Z".split(" ");

	    Sorter<String> sorter = new QuickSorter3Way<String>(items);
	
	    sorter.sort();

	    Assert.assertTrue(AbstractSorter.isSorted(items, 0, items.length-1));

    }*/

}
