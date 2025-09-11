package com.example.enotes_api.controller;

import com.example.enotes_api.dto.UserDto;
import com.example.enotes_api.service.UserService;
import com.example.enotes_api.utils.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
        String url = CommonUtil.getUrl(request);

        boolean registered = userService.register(userDto,url);
        if(registered){
            return CommonUtil.createBuildResponseMessage("Register successfully", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Registration failed",HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
