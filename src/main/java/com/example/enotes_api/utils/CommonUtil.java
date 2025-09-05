package com.example.enotes_api.utils;

import com.example.enotes_api.handler.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonUtil {

    public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus httpStatus){
        return  GenericResponse.builder().httpStatus(httpStatus).status("success").message("success").data(data)
                .build().create();
    }

    public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus httpStatus){
        return GenericResponse.builder().status("success").message(message).httpStatus(httpStatus).build().create();
    }

    public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus httpStatus){
        return GenericResponse.builder().httpStatus(httpStatus).status("failed").message("failed").data(data)
                .build().create();
    }

    public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus httpStatus){
        return GenericResponse.builder().httpStatus(httpStatus).status("failed").message(message)
                .build().create();
    }

}
