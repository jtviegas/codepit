package org.aprestos.labs.snippets.impl.java8.lambdas;

import java.util.Arrays;
import java.util.stream.Stream;

import org.aprestos.labs.snippets.impl.java8.lambdas.domain.SampleData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Chapter3Test {
    
    Chapter3 o = new Chapter3();
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
	
	Assert.assertTrue(o.oneA(Stream.of(2, 4, 6, 12)) == 24);
	Assert.assertTrue(o.oneB(SampleData.getThreeArtists().stream()).equals(
		Arrays.asList("John Coltrane", "US", "John Lennon", "UK", "The Beatles", "UK")));
	Assert.assertTrue(o.two(SampleData.getThreeArtists().stream()) == 5l);
	Assert.assertTrue(o.oneC(SampleData.albums).equals(Arrays.asList(SampleData.manyTrackAlbum)));
	
    }

}
