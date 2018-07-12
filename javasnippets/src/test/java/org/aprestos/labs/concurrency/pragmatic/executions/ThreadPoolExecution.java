package org.aprestos.labs.concurrency.pragmatic.executions;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.aprestos.labs.concurrency.pragmatic.works.Work;

public class ThreadPoolExecution {
	private Work work;
	
	private final int poolSize;

	  public ThreadPoolExecution(double blockingCoefficient, Work work) {
	    
		if(1 < blockingCoefficient || 0 > blockingCoefficient )
			throw new RuntimeException("blockingCoefficient should be in between [0.0,1.0]");
		//one decimal only
		blockingCoefficient = ((int)(blockingCoefficient / 0.1))* 0.1;
		poolSize = (int) (blockingCoefficient == 1.0 ? Runtime.getRuntime().availableProcessors() * 10 : Runtime.getRuntime().availableProcessors() / (1 - blockingCoefficient));
		this.work = work;
	  }

	  public Map<String,Object> execute() {
		
		  final ExecutorService executorPool = Executors.newFixedThreadPool(poolSize);
	    
		try {

			final List<Future<Void>> results= executorPool.invokeAll(work.getWork(), 1000, TimeUnit.SECONDS);
			executorPool.shutdown();
			for (final Future<Void> f : results)
			  f.get();
		} catch (Exception e) {
			if(!executorPool.isTerminated())
				try {  executorPool.shutdownNow();} catch (Exception e1) {}
		}

		return work.getContext();
	}
}
