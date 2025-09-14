package com.example.enotes_api.exception;

public class JwtAuthException extends RuntimeException{

    public JwtAuthException(String message){
        super(message);
    }

}
