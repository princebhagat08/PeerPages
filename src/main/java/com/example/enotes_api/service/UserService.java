package com.example.enotes_api.service;


import com.example.enotes_api.dto.LoginRequest;
import com.example.enotes_api.dto.LoginResponse;
import com.example.enotes_api.dto.UserDto;

public interface UserService {

    public boolean register(UserDto userDto,String url) throws Exception;


    LoginResponse login(LoginRequest loginRequest);
}
