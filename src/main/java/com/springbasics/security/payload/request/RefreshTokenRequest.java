package com.springbasics.security.payload.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String token;
}
