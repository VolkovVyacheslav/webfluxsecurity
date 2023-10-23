package com.volkov.webfluxsecurity.rest;


import com.volkov.webfluxsecurity.dto.AuthRequestDto;
import com.volkov.webfluxsecurity.dto.AuthResponseDto;
import com.volkov.webfluxsecurity.dto.UserDto;
import com.volkov.webfluxsecurity.entity.UserEntity;
import com.volkov.webfluxsecurity.mappers.UserMapper;
import com.volkov.webfluxsecurity.security.CustomPrincipal;
import com.volkov.webfluxsecurity.security.SecurityService;
import com.volkov.webfluxsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthRestControllerV1 {
    private final SecurityService securityService;
    private final UserService userService;


    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserDto dto){
         return userService.registerUser(dto);
    }

    @PostMapping("/login")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto dto){
        return securityService.authenticated(dto.getUsername(), dto.getPassword())
                .flatMap(tokenDetails -> Mono.just(
                        AuthResponseDto.builder()
                                .userId(tokenDetails.getUserId())
                                .token(tokenDetails.getToken())
                                .issuedAt(tokenDetails.getIssuedAt())
                                .expiresAt(tokenDetails.getExpiresAt())
                                .build()
                ));
    }

    @GetMapping("/info")
    public Mono<UserDto> getUserInfo(Authentication authentication){
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return userService.getUserById(principal.getId());
    }

}
