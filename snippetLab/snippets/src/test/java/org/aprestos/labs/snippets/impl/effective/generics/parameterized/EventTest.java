package org.aprestos.labs.snippets.impl.effective.generics.parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class EventTest {
    private static final int n = 12;
    private static int ids = 0;
    
    @Test
    public void test() throws Exception {
	
	List<EventImpl<? extends Number>> events = new ArrayList<EventImpl<? extends Number>>();
	for(int i = 0; i < n; i++ ){
	    events.add(
	    new EventImpl<Integer>(++ids, Arrays.asList(new Integer(3), new Integer(4))));

	}

	
	Assert.assertTrue(true);

    }
}
