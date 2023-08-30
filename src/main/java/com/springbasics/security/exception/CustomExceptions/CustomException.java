package com.springbasics.security.exception.CustomExceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
public class CustomException extends RuntimeException{
    private int code;
    private HttpStatus httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;
    private Boolean success;

    public CustomException(Boolean success,int code,String message,HttpStatus httpStatus){
        super(message);
        this.success=success;
        this.code=code;
        this.httpStatus=httpStatus;
    }
    public CustomException(Boolean success,int code,String message){
        super(message);
        this.success=success;
        this.code=code;
    }
    public CustomException(int code,String message){
        super(message);
        this.success=false;
        this.code=code;
    }
    public CustomException(int code,String message,HttpStatus httpStatus){
        super(message);
        this.httpStatus=httpStatus;
        this.success=false;
        this.code=code;
    }
}
