package org.aprestos.labs.snippets.impl.java8.lambdas;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aprestos.labs.snippets.impl.java8.lambdas.domain.Artist;
import org.aprestos.labs.snippets.impl.java8.lambdas.domain.Performance;
import org.aprestos.labs.snippets.impl.java8.lambdas.domain.SampleData;

public class Chapter4 implements Performance {

    @Override
    public String getName() {
	return "o";
    }

    @Override
    public Stream<Artist> getMusicians() {
	return Stream.concat(
		Stream.of(SampleData.paulMcCartney, SampleData.theBeatles,
			SampleData.johnColtrane),
		SampleData.membersOfTheBeatles.stream());
	
    }

    public void three() {
	Function < Artist , Long > getCount = artist -> artist . getMembers (). count ();
	SampleData.threeArtists().collect(Collectors.maxBy(Comparator.comparing(getCount)));
    }

}

class Artists {
    
    private List<Artist> artists;

    public Artists(List<Artist> artists) {
	this.artists = artists;
    }

    public Optional<Artist> getArtist(int index) {
	Optional<Artist> result = null;
	if (index < 0 || index >= artists.size()) {
	    result = Optional.ofNullable(null);
	}
	else 
	    result = Optional.of(artists.get(index));
	
	return  result;
    }

    public String getArtistName(int index) {

	    Optional<Artist> artist = getArtist(index);
	    if(artist.isPresent())
		return artist.get().getName();
	    else
		return "unknown";

    }
}
