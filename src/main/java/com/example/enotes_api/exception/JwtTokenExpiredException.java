package com.example.enotes_api.exception;

public class JwtTokenExpiredException extends RuntimeException{

    public JwtTokenExpiredException(String message){
        super(message);
    }


}
