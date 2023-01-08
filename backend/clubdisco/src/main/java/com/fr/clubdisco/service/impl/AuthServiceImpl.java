package com.fr.clubdisco.service.impl;

import com.fr.clubdisco.dto.*;
import com.fr.clubdisco.exception.UserAlreadyExistAuthenticationException;
import com.fr.clubdisco.exception.config.ConfigConflictException;
import com.fr.clubdisco.security.jwt.TokenProvider;
import com.fr.clubdisco.service.AuthService;
import com.fr.clubdisco.service.UserService;
import com.fr.clubdisco.util.GeneralUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final TokenProvider tokenProvider;

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        LocalUser localUser = (LocalUser) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, GeneralUtils.buildUserInfo(localUser)));
    }

    @Override
    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
        try {
            userService.registerNewUser(signUpRequest);
        } catch (UserAlreadyExistAuthenticationException e) {
            log.error("Exception Ocurred", e);
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        } catch (ConfigConflictException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
    }
}
