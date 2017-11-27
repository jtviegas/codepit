package org.exercises.jtamv.commons;

import java.util.HashSet;
import java.util.Set;

public class StringOps {

    public static String printStringWithWordsReversed(String S) {
	StringBuffer result = new StringBuffer();
	String[] split = S.split(" ");
	for (int i = 0; i < split.length; i++) {
	    if (i != 0)
		result.append(" ");
	    result.append(reverseWord(split[i]));
	}
	return result.toString();
    }

    public static String reverseWord(String s) {

	int lastIndex = s.length() - 1;
	char[] result = s.toCharArray();

	for (int i = lastIndex, j = 0; i > j; i--, j++) {
	    char a = result[j];
	    result[j] = result[i];
	    result[i] = a;
	}
	return new String(result);
    }

    public static String reverseString(String input) {

	String[] words = input.trim().split(" ");
	StringBuffer sb = new StringBuffer();
	for (int i = words.length - 1; i >= 0; i--) {
	    sb.append(words[i]);
	    sb.append(" ");
	}

	return sb.toString().trim();
    }

    private static final int MAX = 100, MIN = 1;

    public static String printFizzBuzzInteger(int input) {

	if (MIN > input || MAX < input)
	    throw new IllegalArgumentException("only between min and max");

	String solution = null;

	if (0 == input % 5 && 0 == input % 3)
	    solution = "FizzBuzz";
	else if (0 == input % 5)
	    solution = "Buzz";
	else if (0 == input % 3)
	    solution = "Fizz";
	else
	    solution = "" + input;

	System.out.println(solution);

	return solution;
    }

    public static String getCeasarCypher(String input, int key) {
	StringBuffer sb = new StringBuffer();
	String[] words = input.split(" ");
	for (String s : words) {
	    sb.append(cypherWord(s, key));
	    sb.append(" ");
	}

	return sb.toString().trim();
    }

    public static String cypherWord(String input, int key) {

	char[] a = input.toCharArray();
	for (int i = 0; i < a.length; i++)
	    a[i] = cypherChar(a[i], key);

	return new String(a);
    }

    public static char cypherChar(char i, int k) {
	char result = 32;
	if (i > 96 && i < 123) {
	    int c = i - 96;
	    result = (char) (96 + ((c + k) % (122 - 96)));
	} else if (i > 64 && i < 91) {
	    int c = i - 64;
	    result = (char) (64 + ((c + k) % (90 - 64)));
	} else
	    result = i;
	return result;
    }
    
    public static String[] findDuplicateEntries(final String[] airports1,
	    final String[] airports2) {
	// Provide your solution inside the method
	Set<String> dup = new HashSet<String>();
	Set<String> uniq = new HashSet<String>();
	for (String s : airports1) {
	    if (!uniq.add(s))
		dup.add(s);
	}
	for (String s : airports2) {
	    if (!uniq.add(s))
		dup.add(s);
	}
	// Print out the results
	for (String s : dup)
	    System.out.println(s);
	
	return dup.toArray(new String[dup.size()]);
    }

}
