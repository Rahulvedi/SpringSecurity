package com.springbasics.security.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {
    private long userId;
    private String username;
    private String emailAddress;
     private Collection roles;
    private String token;
    private String refreshToken;
}
