package com.example.enotes_api.service;


public interface HomeService {

    Boolean verifyAccount(Integer userId, String verificationCode)throws Exception;
}
