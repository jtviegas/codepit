package org.f1000.tests.jtv.rankjournals.impl;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.f1000.tests.jtv.rankjournals.model.Journal;
import org.f1000.tests.jtv.rankjournals.model.Journal.CATEGORY;

public class RankEngineImpl extends AbstractRankEngine
{
	protected static final float NO_SCORE =-1f;
	
	protected PriorityQueue<Journal> buildQueue(List<Journal> journals)
	{
		PriorityQueue<Journal> prioQueue = new PriorityQueue<Journal>(QUEUE_INITIAL_CAPACITY, new Comparator<Journal>()
				{

					@Override
					public int compare(Journal o1, Journal o2)
					{
						if(o2.getScore() > o1.getScore())
							return 1;
						else
							if(o2.getScore() < o1.getScore())
								return -1;
							else
							{
								return o1.getName().compareTo(o2.getName());
							}
								
					}
				});
				
		for(Journal j:journals)
		{
			if(CATEGORY.REVIEW != j.getCategory())
				prioQueue.add(j);
		}
		
		
		return prioQueue;
	}
	
	protected Map<Journal, Integer> buildMap(PriorityQueue<Journal> prioQueue)
	{
		Map<Journal, Integer> result = new LinkedHashMap<Journal, Integer>();
		int rank = 0;
		float currentScore = NO_SCORE;
		Journal journal = null;
		
		while(null != prioQueue.peek())
		{
			journal = prioQueue.poll();
			
			if((NO_SCORE == currentScore) || (journal.getScore() != currentScore))
			{
				//first journal or journal with different score
				rank=result.size();
				currentScore = journal.getScore();
				result.put(journal, ++rank);
			}
			else
			{
				//another journal with the same score
				result.put(journal, rank);
			}

		}
		
		return result;
	}

}
