package org.aprestos.labs.snippets.impl.java8.lambdas;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.aprestos.labs.snippets.impl.java8.lambdas.domain.Artist;
import org.aprestos.labs.snippets.impl.java8.lambdas.domain.SampleData;

public class Chapter5 {



    public void o() {
	Function < Artist , Long > getCount = artist -> artist . getMembers (). count ();
	SampleData.threeArtists().collect(Collectors.maxBy(Comparator.comparing(getCount)));
	
	double average = SampleData.albums.collect ( Collectors.averagingInt ( album -> album .getTrackList().size ()));
    }




}
