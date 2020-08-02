package com.chen.study.mapper;

import com.chen.study.entity.User1;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserMapper extends ReactiveMongoRepository<User1,String> {
}
