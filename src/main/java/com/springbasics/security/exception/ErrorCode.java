package com.springbasics.security.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    UNAUTHORIZED_ACCESS(1000,"Full authentication is required to access this resource",HttpStatus.UNAUTHORIZED),
    USERNAME_ALREADY_EXIST(1001,"Username already exist!",HttpStatus.CONFLICT),
    EMAIL_ALREADY_EXIST(1002,"Email address already exist!",HttpStatus.CONFLICT),
    EMAIL_NOT_EXIST(1003,"Email address not exist!",HttpStatus.NOT_FOUND),


//    Service Down Errors
    EMAIL_SERVICE_DOWN(2000,"Unable to send emails.",HttpStatus.INTERNAL_SERVER_ERROR),


    //JWT Token and Refresh Token and OTP
    OTP_EXPIRED(10000,"OTP is expired. Please generate a new OTP.",HttpStatus.INTERNAL_SERVER_ERROR),
    OTP_NOT_MATCHED(10001,"Sorry, the OTP you entered is not valid. Please check your OTP and try again.",HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED(10002,"Refresh token is expired. Please make a new signin request.",HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_NOT_FOUND(10003,"Refresh token not found. Please pass a valid refresh token",HttpStatus.BAD_REQUEST);
    private final Integer codeValue;
    private final String responseMessage;
    private HttpStatus status;
     ErrorCode(int codeValue) {
        this.codeValue = codeValue;
        this.responseMessage = "Something went wrong. Please try again after some time.";
    }

     ErrorCode(int codeValue, String message,HttpStatus status) {
        this.codeValue = codeValue;
        this.responseMessage = message;
        this.status = status;
    }

    public Integer getCodeValue() {
        return this.codeValue;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return this.name() + "(" + this.getCodeValue()+")";
    }
}
