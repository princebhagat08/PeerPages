package com.example.enotes_api.service;

import com.example.enotes_api.dto.PasswordChangeRequest;

public interface UserService {

    void changePassword(PasswordChangeRequest changeRequest);
}
