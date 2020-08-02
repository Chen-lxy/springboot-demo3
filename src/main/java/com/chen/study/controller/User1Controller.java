package com.chen.study.controller;


import com.chen.study.entity.User1;
import com.chen.study.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/user1")
public class User1Controller {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/list")
    public Flux<User1> getAll(){
        return userMapper.findAll();
    }

    @GetMapping(value = "/listdelay",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<User1> geAlldelay(){
        return userMapper.findAll().delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User1>> getUser(@PathVariable String id){
        return userMapper.findById(id)
                .map(getUser -> ResponseEntity.ok(getUser))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public Mono<User1> createUser(User1 user){
        return userMapper.save(user);
    }

    @PutMapping("/{id}")
    public Mono updateUser(@PathVariable(value = "id") String id,User1 user){
        return userMapper.findById(id)
                .flatMap(exisitingUser -> {
                    exisitingUser.setName(user.getName());
                    return userMapper.save(exisitingUser);
                })
                .map(updateUser -> new ResponseEntity<>(updateUser, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletUser(@PathVariable(value = "id") String id){
        return userMapper.findById(id)
                .flatMap(existingUser ->
                                userMapper.delete(existingUser)
                                    .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
