package org.aprestos.labs.snippets.impl.java8.lambdas;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aprestos.labs.snippets.impl.java8.lambdas.domain.Artist;
import org.aprestos.labs.snippets.impl.java8.lambdas.domain.SampleData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Chapter4Test {
    
    Chapter3 o = new Chapter3();
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
	
	Set<Artist> expected = Stream.concat(Stream.of(SampleData.theBeatles, SampleData.paulMcCartney, SampleData.johnColtrane ), 
		SampleData.membersOfTheBeatles.stream() ).collect(Collectors.toSet());
	Set<Artist> actual = new Chapter4().getAllMusicians().collect(Collectors.toSet());

	Assert.assertTrue(expected.equals(actual));

	
    }

}

