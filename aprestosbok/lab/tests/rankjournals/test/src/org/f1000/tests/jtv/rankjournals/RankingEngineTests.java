package org.f1000.tests.jtv.rankjournals;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.f1000.tests.jtv.rankjournals.impl.RankEngineImpl;
import org.f1000.tests.jtv.rankjournals.interfaces.RankEngine;
import org.f1000.tests.jtv.rankjournals.model.Journal;
import org.f1000.tests.jtv.rankjournals.model.Journal.CATEGORY;
import org.f1000.tests.jtv.rankjournals.utils.RankPrinter;
import org.junit.Test;

public class RankingEngineTests
{
	RankPrinter printer = new RankPrinter();
	
	@Test
	public void TestScenario1()
	{
		List<Journal> journalsOne = Arrays.asList(new Journal("Journal A",5.6f),new Journal("Journal B",2.4f),new Journal("Journal C",3.1f));
		Map<Journal, Integer> expectedRankingsOne = new LinkedHashMap<Journal,Integer>();
		expectedRankingsOne.put(new Journal("Journal A",5.6f), 1);
		expectedRankingsOne.put(new Journal("Journal C",3.1f), 2);
		expectedRankingsOne.put(new Journal("Journal B",2.4f), 3);
		
		
		RankEngine engine = new RankEngineImpl();
		engine.loadData(journalsOne);
		Map<Journal, Integer> rankings = engine.computeRankings();
		org.junit.Assert.assertEquals(expectedRankingsOne, rankings);

		printer.print(System.out, rankings);

	}
	
	@Test
	public void TestScenario2()
	{
		List<Journal> journalsTwo  = Arrays.asList(new Journal("Journal A",2.2f),new Journal("Journal B",6.2f),new Journal("Journal C",6.2f));
		Map<Journal, Integer> expectedRankingsTwo = new LinkedHashMap<Journal,Integer>();
		expectedRankingsTwo.put(new Journal("Journal B",6.2f), 1);
		expectedRankingsTwo.put(new Journal("Journal C",6.2f), 1);
		expectedRankingsTwo.put(new Journal("Journal A",2.2f), 3);
		
		RankEngine engine = new RankEngineImpl();
		engine.loadData(journalsTwo);
		Map<Journal, Integer> rankings = engine.computeRankings();
		org.junit.Assert.assertEquals(expectedRankingsTwo, rankings);
		
		printer.print(System.out, rankings);

	}
	
	@Test
	public void TestScenario3()
	{
		List<Journal> journalsThree = Arrays.asList(new Journal("Journal A",5.6f, CATEGORY.REVIEW),new Journal("Journal B",2.4f),new Journal("Journal C",3.1f));
		Map<Journal, Integer> expectedRankingsThree = new LinkedHashMap<Journal,Integer>();
		expectedRankingsThree.put(new Journal("Journal C",3.1f), 1);
		expectedRankingsThree.put(new Journal("Journal B",2.4f), 2);
		
		RankEngine engine = new RankEngineImpl();
		engine.loadData(journalsThree);
		Map<Journal, Integer> rankings = engine.computeRankings();
		org.junit.Assert.assertEquals(expectedRankingsThree, rankings);
		
		printer.print(System.out, rankings);

	}

}