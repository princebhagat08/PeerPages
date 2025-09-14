package com.example.enotes_api.service;


import com.example.enotes_api.dto.LoginRequest;
import com.example.enotes_api.dto.LoginResponse;
import com.example.enotes_api.dto.UserRequest;

public interface AuthService {

    public boolean register(UserRequest userRequest, String url) throws Exception;


    LoginResponse login(LoginRequest loginRequest);
}
