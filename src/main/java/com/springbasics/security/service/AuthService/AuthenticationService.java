package com.springbasics.security.service.AuthService;

import com.springbasics.security.model.OTP;
import com.springbasics.security.payload.request.EmailVerificationRequest;
import com.springbasics.security.payload.request.RefreshTokenRequest;
import com.springbasics.security.payload.request.SignInRequest;
import com.springbasics.security.payload.request.SignUpRequest;
import org.springframework.http.ResponseEntity;


public interface AuthenticationService {
    public ResponseEntity<?> signIn(SignInRequest signInRequest);
    public ResponseEntity<?> signUp(SignUpRequest signUpRequest);
    public ResponseEntity<?> emailVerification(EmailVerificationRequest emailVerificationRequest);
    public ResponseEntity<?> verifyEmailOtp(OTP otp);
    public ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
