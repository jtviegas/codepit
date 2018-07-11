package org.aprestos.labs.concurrency.pragmatic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class AsyncExecution<W, R> {

  private final int poolSize;

  public AsyncExecution(double blockingCoefficient) {
    
	if(1 < blockingCoefficient || 0 > blockingCoefficient )
		throw new RuntimeException("blockingCoefficient should be in between [0.0,1.0]");
	//one decimal only
	blockingCoefficient = ((int)(blockingCoefficient / 0.1))* 0.1;
	poolSize = (int) (blockingCoefficient == 1.0 ? Runtime.getRuntime().availableProcessors() * 10 : Runtime.getRuntime().availableProcessors() / (1 - blockingCoefficient));
  }

  public List<R> execute(final Function<List<W>, List<List<W>>> partitioningRationale, final Function<List<W>, R> function, final List<W> arguments)
      throws InterruptedException, ExecutionException {
	
	List<List<W>> workPartitions = partitioningRationale.apply(arguments);
    List<Callable<R>> partitions = new ArrayList<>();
    for( List<W> workPartition: workPartitions) 
    	partitions.add(new Callable<R>() {
            @Override
            public R call() throws Exception {
              return function.apply(workPartition);
            }
          });
    
    List<R> result = new ArrayList<R>();
    final ExecutorService executorPool = Executors.newFixedThreadPool(poolSize);
	try {
		final List<Future<R>> resultFromParts = executorPool.invokeAll(partitions, 1000, TimeUnit.SECONDS);
		executorPool.shutdown();
		for (final Future<R> f : resultFromParts)
		  result.add(f.get());
	} catch (Exception e) {
		if(!executorPool.isTerminated())
			try {  executorPool.shutdownNow();} catch (Exception e1) {}
	}

    return result;
  }
  
}
