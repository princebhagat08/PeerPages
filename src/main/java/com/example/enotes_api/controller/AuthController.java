package com.example.enotes_api.controller;

import com.example.enotes_api.dto.LoginRequest;
import com.example.enotes_api.dto.LoginResponse;
import com.example.enotes_api.dto.UserDto;
import com.example.enotes_api.service.UserService;
import com.example.enotes_api.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("/")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto){
        boolean registered = userService.register(userDto);
        if(registered){
            return CommonUtil.createBuildResponseMessage("Register successfully", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Registration failed",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        LoginResponse login = userService.login(loginRequest);
        if(ObjectUtils.isEmpty(login)){
            return CommonUtil.createErrorResponseMessage("Invalid credentials",HttpStatus.BAD_REQUEST);
        }
        return CommonUtil.createBuildResponse(login,HttpStatus.OK);
    }

}
