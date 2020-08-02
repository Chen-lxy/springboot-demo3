package com.chen.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloWorldController {

    @GetMapping("/helloworld")
    public Mono<String> helloworld(){
        return Mono.just("This is WebFlux demo");
    }
}
