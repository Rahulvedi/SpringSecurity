package com.springbasics.security.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank
    @Size(min = 3,max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String emailaddress;
    @NotBlank
    @Size(min = 6,max = 40)
    private String password;
    @NotBlank
    @Size(min = 6,max = 6)
    private Long otp;

}
