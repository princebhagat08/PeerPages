package com.example.enotes_api.endpoint;

import com.example.enotes_api.dto.LoginRequest;
import com.example.enotes_api.dto.UserRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {

    @PostMapping("/")
    ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest, HttpServletRequest request) throws Exception;

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest);


    }
