package org.aprestos.labs.libs.reactor;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.reactivestreams.Publisher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Example {

  private void doSomething() {
    
    Flux<Integer> just = Flux.just(1, 2, 3);
    
    Mono<String> just2 = Mono.just("foo");
    
    Publisher<String> just3 = Mono.just("foo2");
      
    
    List<Integer> elements = new ArrayList<>();
    just.log().subscribe(elements::add);

  }

  @Test
  public void test() throws Exception {
    
    doSomething();

  }

}
