package org.f1000.tests.jtv.rankjournals.impl;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.f1000.tests.jtv.rankjournals.interfaces.RankEngine;
import org.f1000.tests.jtv.rankjournals.model.Journal;

/**
 * Abstract implementation for a RankingEngine,
 * loads the journals in a priority queue and later retrieves
 * it already sorted according to rationale implemented in buildQueue.
 * 
 * @author joao.viegas
 *
 */
public abstract class AbstractRankEngine implements RankEngine
{
	protected static final int QUEUE_INITIAL_CAPACITY = 32;
	protected List<Journal> journals = null;
	
	public AbstractRankEngine(){ }

	@Override
	public void loadData(List<Journal> journals)
	{
		this.journals = journals;
	}

	@Override
	public Map<Journal, Integer> computeRankings()
	{
		PriorityQueue<Journal> prioQueue = buildQueue(this.journals);
		Map<Journal, Integer> map = buildMap(prioQueue);
		
		return map;
	}
	
	/**
	 * builds a priority queue sorting elements by specific rationale
	 * @param journals
	 * @return
	 */
	protected abstract PriorityQueue<Journal> buildQueue(List<Journal> journals);
	
	/**
	 * retrieves the sorted elements from priority queue and creates a ranking map 
	 * @param prioQueue
	 * @return
	 */
	protected abstract Map<Journal, Integer> buildMap(PriorityQueue<Journal> prioQueue);

}
