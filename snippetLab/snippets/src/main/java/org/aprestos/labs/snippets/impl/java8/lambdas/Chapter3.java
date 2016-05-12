package org.aprestos.labs.snippets.impl.java8.lambdas;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aprestos.labs.snippets.impl.java8.lambdas.domain.Album;
import org.aprestos.labs.snippets.impl.java8.lambdas.domain.Artist;

public class Chapter3 {

    int oneA(Stream<Integer> numbers) {
	return numbers.reduce((acc, elem) -> acc + elem).get();
    }
    
    List<String> oneB(Stream<Artist> artists) {
   	return artists.flatMap(artist -> Stream.of(artist.getName(), artist.getNationality())).collect(Collectors.toList());
    }
    
    List<Album> oneC(Stream<Album> albums) {
   	return albums.filter(album -> album.getTrackList().size() > 3).collect(Collectors.toList());
    }

    long two(Stream<Artist> artists) {
	long result = artists.flatMap( artist -> artist.getMembers() ).count();
	return result;
    }

}
