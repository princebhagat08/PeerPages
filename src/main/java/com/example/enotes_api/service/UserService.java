package com.example.enotes_api.service;

import com.example.enotes_api.dto.PasswordChangeRequest;
import com.example.enotes_api.dto.PasswordResetRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    void changePassword(PasswordChangeRequest changeRequest);

    void sendPasswordResetEmail(String email, HttpServletRequest request) throws Exception;

    void verifyPasswordResetLink(Integer uid, String code) throws Exception;

    void resetPassword(PasswordResetRequest resetRequest) throws Exception;
}
