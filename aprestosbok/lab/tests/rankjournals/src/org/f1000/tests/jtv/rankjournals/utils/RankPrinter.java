package org.f1000.tests.jtv.rankjournals.utils;

import java.io.PrintStream;
import java.util.Map;

import org.f1000.tests.jtv.rankjournals.model.Journal;

/**
 * generic printer implementation, prints the ranking in a stream
 * @author joao.viegas
 *
 */
public class RankPrinter
{
	private static final String HEADER = "RANK\t\tJournal\t\tScore";
	
	public void print(PrintStream writer, Map<Journal, Integer> ranking) 
	{
		Journal journal = null;
		Integer rank = null;

		writer.println(System.getProperty("line.separator"));
		writer.println(HEADER);
		
		for (Map.Entry<Journal,Integer> entry : ranking.entrySet()) 
		{
			journal = entry.getKey();
			rank = entry.getValue();
			writer.println(rank.toString() + "\t\t" + journal.getName() + "\t\t" + journal.getScore());

		}
	}

}
