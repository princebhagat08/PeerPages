package com.example.enotes_api.controller;

import com.example.enotes_api.dto.PasswordChangeRequest;
import com.example.enotes_api.dto.UserRequest;
import com.example.enotes_api.dto.UserResponse;
import com.example.enotes_api.endpoint.UserEndpoint;
import com.example.enotes_api.entity.User;
import com.example.enotes_api.service.UserService;
import com.example.enotes_api.utils.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController implements UserEndpoint {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<?> getProfile(){

        User user = CommonUtil.getLoggedInUser();
        UserResponse userResponse = mapper.map(user, UserResponse.class);
        return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> changePassword( PasswordChangeRequest changeRequest){

        userService.changePassword(changeRequest);
        return CommonUtil.createBuildResponseMessage("Password change successfully", HttpStatus.OK);
    }

}
