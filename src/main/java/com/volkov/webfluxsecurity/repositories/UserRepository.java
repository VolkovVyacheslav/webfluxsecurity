package com.volkov.webfluxsecurity.repositories;


import com.volkov.webfluxsecurity.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<UserEntity, Long> {

    Mono<UserEntity> findByUsername(String username);
}
