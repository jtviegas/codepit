package com.chp.tests.cycles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CycleDetection {

	private static final String USAGE = " java -jar <jar> <file> - should always provide the file as the only argument !!!";

	public static void main(String[] args) {
		String line = null;
		BufferedReader in = null;

		if (args.length != 1) {
			System.out.println(USAGE);
			System.exit(-1);
		}

		try {

			in = new BufferedReader(new FileReader(new File(args[0])));
			PatternFinder _p = new PatternFinderImpl();
			while ((line = in.readLine()) != null) {
				System.out.println(_p.find(line.trim()));
			}

		} catch (IOException e) {
			e.printStackTrace(System.out);
		} finally {
			if (null != in)
				try {
					in.close();
				} catch (IOException e) {/* forget about this exception */
				}

			in = null;
			line = null;
		}

	}

	

}

interface PatternFinder 
{

	public abstract String find(String text);

}

class PatternFinderImpl implements PatternFinder {

	/* (non-Javadoc)
	 * @see com.chp.tests.cycles.PatternFinder#find(java.lang.String)
	 */
	public String find(String text) {
		String _pattern = null, _subText = null;
		// utility arrays
		String[] _arrPattern = null, _arrText = null;

		// I'm going to treat the line as text, not numbers
		_arrText = text.split(" ");

		/*
		 * starting in the beginning of the text...
		 */
		for (int _i = 0; _i < _arrText.length; _i++) {
			/*
			 * computes all existing patterns from that position on....
			 */
			for (int _j = 1; _j < _arrText.length; _j++) {
				_arrPattern = Arrays.copyOfRange(_arrText, _i, _i + _j);
				_pattern = join(_arrPattern);
				/*
				 * if such a pattern occurs x times and if that means
				 * somehow that it simply is repeated till the end of the
				 * string then we found the first repeating pattern
				 */
				_subText = join(Arrays.copyOfRange(_arrText, _i,
						_arrText.length));

				if (_subText.length() == find(_pattern, _subText)
						* _pattern.length()) {
					return join(_arrPattern, " ");
				}

			}
		}

		return "";
	}

	/**
	 * finds number of occurrences of a specific pattern in text starting in
	 * a specific index
	 * 
	 * @param pattern
	 * @param text
	 * @param _index
	 * @return
	 */

	private int find(String _pattern, String _text) {
		int _result = 0;
		int _index = 0;

		/**
		 * 34 12 11 34 12 34 12
		 */

		/*
		 * we have to verify the case where the pattern is itself the text -
		 * we discard this solution
		 */
		if (_pattern.length() == _text.length())
			return 0;

		while (_index + _pattern.length() <= _text.length()
				&& areEqual(_pattern.toCharArray(),
						_text.substring(_index, _index + _pattern.length())
								.toCharArray()))

		{
			_index += _pattern.length();
			_result++;
		}

		return _result;
	}

	/**
	 * computes a numerical hash of a string
	 * 
	 * @param _s
	 * @return
	 */
	private int computeHash(char[] _s) {
		int _result = 1;
		for (char _c : _s)
			_result *= _c;

		return _result;
	}

	/**
	 * compares strings, trying to use its length and hash as primary
	 * sources of comparison and only at the last moment checks text
	 * equality
	 * 
	 * @param _a
	 * @param _b
	 * @return
	 */
	private boolean areEqual(char[] _a, char[] _b) {
		boolean _result = false;

		if (_a.length == _b.length && computeHash(_a) == computeHash(_b)) {
			int _i = 0;
			while (_i < _a.length && _a[_i] == _b[_i])
				_i++;

			if (_i == (_a.length))
				_result = true;

		}

		return _result;
	}

	private String join(String[] _arrS, String _delim) {
		StringBuffer _sb = new StringBuffer();

		int _index = 0;
		for (String _s : _arrS) {
			if (0 == _index)
				_sb.append(_s);
			else
				_sb.append(null == _delim ? _s : _delim + _s);

			_index++;
		}

		return _sb.toString();
	}

	private String join(String[] _arrS) {
		return join(_arrS, null);
	}

}
