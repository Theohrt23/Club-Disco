package com.fr.clubdisco.service;

import com.fr.clubdisco.dto.LoginRequest;
import com.fr.clubdisco.dto.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);

    ResponseEntity<?> registerUser(SignUpRequest signUpRequest);

}
