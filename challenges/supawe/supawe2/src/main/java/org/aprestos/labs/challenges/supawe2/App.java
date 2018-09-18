package org.aprestos.labs.challenges.supawe2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App {
	private static final String NO_ARG = "must provide a file path as a mandatory argument";
	private static final String NO_FILE = "not a readable file: %s !";
	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		logger.debug("[App|in] {}", args);
		if (1 != args.length) {
			System.out.println(NO_ARG);
			return;
		}

		Path file = Paths.get(args[0]);
		if ((!Files.exists(file, LinkOption.NOFOLLOW_LINKS)) || (!file.toFile().canRead())) {
			System.out.println(String.format(NO_FILE, file.toString()));
			return;
		}

		Map<Integer, Set<String>> anagramsMap = new App().handle(file);
		for (Set<String> map : anagramsMap.values()) {
			System.out.println(map.toString());
		}

		logger.debug("[App|out]");
	}

	Map<Integer, Set<String>> handle(Path file) {
		logger.debug("[handle|in] {}", file);
		BufferedReader reader = null;
		Map<Integer, Set<String>> anagramsMap = new HashMap<Integer, Set<String>>();

		try {

			reader = Files.newBufferedReader(file);
			String line = null;
			int index = 0;
			while ((line = reader.readLine()) != null && 0 < line.trim().length()) {
				String term = line.trim();
				logger.info(String.format("reading line index: %d", index++));
				int anagramHasCode = getAnagramSpecHashCode(term);
				Set<String> anagrams = anagramsMap.get(Integer.valueOf(anagramHasCode));
				if (null == anagrams) {
					anagrams = new HashSet<String>();
					anagramsMap.put(Integer.valueOf(anagramHasCode), anagrams);
				}
				anagrams.add(term);
			}

		} catch (Exception e1) {
			logger.error("[handle]...ooppss...", e1);
		} finally {
			if (null != reader)
				try {
					reader.close();
				} catch (IOException e2) {
					logger.error("[handle]...when trying to close the reader...", e2);
				}
		}
		logger.debug("[handle|out]");
		return anagramsMap;
	}

	int getAnagramSpecHashCode(String s) {
		int[] spec = new int[256];
		for (char c : s.toCharArray())
			spec[c]++;

		return Arrays.hashCode(spec);
	}

}
