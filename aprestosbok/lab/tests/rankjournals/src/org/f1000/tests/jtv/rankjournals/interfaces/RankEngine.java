package org.f1000.tests.jtv.rankjournals.interfaces;

import java.util.List;
import java.util.Map;

import org.f1000.tests.jtv.rankjournals.model.Journal;

/**
 * General interface for a component that 
 * provides ranking creation functionality
 * 
 * @author joao.viegas
 *
 */
public interface RankEngine
{
	void loadData(List<Journal> journals);
	Map<Journal,Integer> computeRankings();
}
