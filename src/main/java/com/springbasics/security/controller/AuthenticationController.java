package com.springbasics.security.controller;


import com.springbasics.security.model.OTP;
import com.springbasics.security.payload.request.EmailVerificationRequest;
import com.springbasics.security.payload.request.RefreshTokenRequest;
import com.springbasics.security.payload.request.SignInRequest;
import com.springbasics.security.payload.request.SignUpRequest;
import com.springbasics.security.service.AuthService.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "${spring.security.BASE_URL}")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest){
        return authenticationService.signIn(signInRequest);
    }
    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest){
        return authenticationService.signUp(signUpRequest);
    }
    @PostMapping("/auth/email-verification")
    public ResponseEntity<?> emailVerification(@RequestBody EmailVerificationRequest emailVerificationRequest){
        return authenticationService.emailVerification(emailVerificationRequest);
    }
    @PostMapping("/auth/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return authenticationService.refreshToken(refreshTokenRequest);
    }


}
