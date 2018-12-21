package org.aprestos.labs.lang.java8.lambdas;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

public class SpliteratorExperiment {

	@Test
	public void creational() {

		List<BigDecimal> bds = Stream.iterate(BigDecimal.ONE, n -> n.add(BigDecimal.ONE)).limit(12)
				.collect(Collectors.toList());

		Stream.generate(RandomUtils::nextInt).limit(12).forEach(System.out::println);

		assertEquals(false, Arrays.asList("jonathan", "maura", "mario", "richard", "nordis", "manpul", "firefighter")
				.stream().allMatch(s -> s.startsWith("f")));

		

	}
	
	class TestingSplits implements Spliterator<Integer>{
	  
	  private List<Integer> list;
	  private int index;
	  
	  TestingSplits(List<Integer> list){
	    this.list = list;
	    this.index = 0;
	  }
	  
    @Override
    public boolean tryAdvance(Consumer<? super Integer> action) {
      boolean result = false;
      if( index < list.size() ) {
        action.accept(list.get(index++));
        result = true;
      }
      return result;
    }

    @Override
    public Spliterator<Integer> trySplit() {
      
      return null;
    }

    @Override
    public long estimateSize() {
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public int characteristics() {
      // TODO Auto-generated method stub
      return 0;
    }


	  
	}



}
