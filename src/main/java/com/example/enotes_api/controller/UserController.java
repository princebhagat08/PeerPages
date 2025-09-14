package com.example.enotes_api.controller;

import com.example.enotes_api.dto.UserRequest;
import com.example.enotes_api.dto.UserResponse;
import com.example.enotes_api.entity.User;
import com.example.enotes_api.utils.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(){

        User user = CommonUtil.getLoggedInUser();
        UserResponse userResponse = mapper.map(user, UserResponse.class);
        return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);

    }
}
