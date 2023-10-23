package com.volkov.webfluxsecurity.service;


import com.volkov.webfluxsecurity.dto.UserDto;
import com.volkov.webfluxsecurity.entity.UserEntity;
import com.volkov.webfluxsecurity.entity.UserRole;
import com.volkov.webfluxsecurity.mappers.UserMapper;
import com.volkov.webfluxsecurity.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service


public class UserService {
    private  UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private  UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public Mono<UserDto> registerUser(UserDto user){
        UserEntity userEntity = new UserEntity();
               return userRepository.save(userEntity.toBuilder()
                        .username(user.getUsername())
                        .password(passwordEncoder.encode(user.getPassword()))
                        .role(UserRole.USER)
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .enabled(true)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                .build()
        ).doOnSuccess(u -> {
            log.info("IN registerUser - user: {} created", u);
        }).map(userMapper::map);
    }

    public Mono<UserDto> getUserById(Long id){
        return userRepository.findById(id)
                .map(userMapper::map);
    }

    public Mono<UserDto> getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .map(userMapper::map);
    }
}
