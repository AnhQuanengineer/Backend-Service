package com.example.backendservice.service.impl;

import com.example.backendservice.common.TokenType;
import com.example.backendservice.controller.request.SignInRequest;
import com.example.backendservice.controller.response.TokenResponse;
import com.example.backendservice.model.UserEntity;
import com.example.backendservice.repository.UserRepository;
import com.example.backendservice.service.AuthenticationService;
import com.example.backendservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION-SERVICE")
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public TokenResponse getAccessToken(SignInRequest request) {
        log.info("get access token");

        List<String> authorities = new ArrayList<>();
        try {
            Authentication authenticate= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            log.info("isAuthenticated: {}", authenticate.isAuthenticated());
            log.info("Authorities: {}", authenticate.getAuthorities().toString());
            authorities.add(authenticate.getAuthorities().toString());

            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (AuthenticationException e) {
            log.error("Logging fail, message = {}", e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        }

        String accessToken =  jwtService.generateAccessToken(request.getUsername(), authorities);
        String refreshToken =  jwtService.generateRefreshToken(request.getUsername(), authorities);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponse getRefreshToken(String refreshToken) {
//        log.info("get refresh token");
//        if (!StringUtils.hasLength(refreshToken)) {
//            try {
//                String userName = jwtService.extractUsername(refreshToken, TokenType.REFRESH_TOKEN);
//
//                UserEntity user = userRepository.findByUsername(userName);
//                List<String> authorities = new ArrayList<>();
//                user.getAuthorities().forEach(authority -> authorities.add(authority.getAuthority()));
//
//                String accessToken = jwtService.generateAccessToken(user.getUsername(), authorities);
//
//                return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
//            } catch (Exception e){
//                log.error("Access denied, message = {}", e.getMessage());
//                throw new ForBiddenException(e.getMessage());
//            }
//        }
        return null;
    }
}
