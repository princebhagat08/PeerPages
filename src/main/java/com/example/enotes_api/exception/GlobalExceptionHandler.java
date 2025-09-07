package com.example.enotes_api.exception;

import com.example.enotes_api.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;

@Slf4j
@ControllerAdvice  // @ControllerAdvice = A way to write centralized logic for all controllers.
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(Exception e){
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(Exception e){
        log.error("GlobalExceptionHandler : handleResourceNotFoundException() : {}", e.getMessage());
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e){
        log.error("GlobalExceptionHandler : handleValidationException() : {}", e.getMessage());
//        return new ResponseEntity<>(e.getError(), HttpStatus.BAD_REQUEST);
        return CommonUtil.createErrorResponse(e.getError(),HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ExistDataException.class)
    public ResponseEntity<?> handleExistDataException(ExistDataException e){
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleExistDataException(FileNotFoundException e){
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.NOT_FOUND);
    }


}
