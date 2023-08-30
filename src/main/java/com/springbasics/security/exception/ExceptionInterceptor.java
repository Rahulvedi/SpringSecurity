package com.springbasics.security.exception;

import com.springbasics.security.exception.CustomExceptions.CustomException;
import com.springbasics.security.exception.CustomExceptions.CustomExceptionSchema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionInterceptor {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handlePathNotFoundException(CustomException exception){
        CustomExceptionSchema response=new CustomExceptionSchema(exception.getSuccess(),exception.getCode(),exception.getMessage());
        return new ResponseEntity<>(response,exception.getHttpStatus());
    }
}
