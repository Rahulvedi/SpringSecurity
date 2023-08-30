package com.springbasics.security.payload.response;

import lombok.Data;

@Data
public class MessageRespone {
    private Boolean success=true;
    private String message;

    public MessageRespone(String message) {
        this.message = message;
    }
}
