package org.aprestos.labs.challenges.supawe2;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	public void testApp() {

		@SuppressWarnings("unchecked")
		List<Set<String>> expected = Arrays.asList(
				(Set<String>) new HashSet<String>(Arrays.asList("abc", "cba", "bac")),
				(Set<String>) new HashSet<String>(Arrays.asList("unf", "fun")),
				(Set<String>) new HashSet<String>(Arrays.asList("hello")));

		Path file = Paths.get(AppTest.class.getClassLoader().getResource("example.txt").getPath());
		Map<Integer, Set<String>> map = new App().handle(file);
		for (Map.Entry<Integer, Set<String>> e : map.entrySet()) {
			System.out.println(e.getValue().toString());

			boolean found = false;
			for (Set<String> s : expected) {
				if (s.containsAll(e.getValue())) {
					found = true;
					break;
				}
			}
			Assert.assertTrue(found);

		}

	}

	@Test
	public void test_anagram() {

		assertEquals(new App().getAnagramSpecHashCode("joaoa"), new App().getAnagramSpecHashCode("ajoao"));
		Assert.assertNotEquals(new App().getAnagramSpecHashCode("joaoa"), new App().getAnagramSpecHashCode("ajoaox"));

	}

}
