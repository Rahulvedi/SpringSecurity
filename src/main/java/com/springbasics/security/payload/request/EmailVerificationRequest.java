package com.springbasics.security.payload.request;

import lombok.Data;

@Data
public class EmailVerificationRequest {
    private String email;
}
