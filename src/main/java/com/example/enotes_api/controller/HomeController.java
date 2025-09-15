package com.example.enotes_api.controller;

import com.example.enotes_api.dto.PasswordResetRequest;
import com.example.enotes_api.endpoint.HomeEndpoint;
import com.example.enotes_api.service.HomeService;
import com.example.enotes_api.service.UserService;
import com.example.enotes_api.utils.CommonUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController implements HomeEndpoint {

    @Autowired
    private HomeService homeService;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<?> verifyUserAccount(Integer uid,  String code)throws Exception{

        Boolean verifyAccount = homeService.verifyAccount(uid, code);
        if(verifyAccount){
            return CommonUtil.createBuildResponseMessage("Account verification success", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Invalid verification link",HttpStatus.BAD_REQUEST);
    }


    @Override
    public ResponseEntity<?> sendResendEmail( String email, HttpServletRequest request) throws  Exception{
        userService.sendPasswordResetEmail(email,request);
        return CommonUtil.createBuildResponseMessage("Check email and reset password",HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> verifyPasswordResetLink(Integer uid,  String code) throws Exception {
        userService.verifyPasswordResetLink(uid,code);
        return CommonUtil.createBuildResponseMessage("verification success",HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> resetPassword( PasswordResetRequest resetRequest) throws Exception{
        userService.resetPassword(resetRequest);
        return  CommonUtil.createBuildResponseMessage("Password reset successfully",HttpStatus.OK);
    }

}
