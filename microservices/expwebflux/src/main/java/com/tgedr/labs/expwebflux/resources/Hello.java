package com.tgedr.labs.expwebflux.resources;

import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hello")
public class Hello {

    @GetMapping
    public Publisher<String> hello() {
        return Mono.just("hello pa");
    }

}
