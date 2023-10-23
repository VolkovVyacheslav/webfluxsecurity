package com.volkov.webfluxsecurity.security;

import com.volkov.webfluxsecurity.dto.UserDto;
import com.volkov.webfluxsecurity.exceptions.UnauthorizedException;
import com.volkov.webfluxsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final UserService userService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return userService.getUserById(principal.getId())
                .filter(UserDto::isEnabled)
                .switchIfEmpty(Mono.error(new UnauthorizedException("User disable")))
                .map(user -> authentication);
    }
}
