package com.springbasics.security.service.AuthService;

import com.springbasics.security.config.jwt.JWTHelper;
import com.springbasics.security.exception.CustomExceptions.CustomException;
import com.springbasics.security.exception.ErrorCode;
import com.springbasics.security.model.Authority;
import com.springbasics.security.model.OTP;
import com.springbasics.security.model.RefreshToken;
import com.springbasics.security.model.User;
import com.springbasics.security.payload.request.EmailVerificationRequest;
import com.springbasics.security.payload.request.RefreshTokenRequest;
import com.springbasics.security.payload.request.SignInRequest;
import com.springbasics.security.payload.request.SignUpRequest;
import com.springbasics.security.payload.response.MessageRespone;
import com.springbasics.security.payload.response.RefreshTokenResponse;
import com.springbasics.security.payload.response.SignInResponse;
import com.springbasics.security.repository.AuthorityRepository;
import com.springbasics.security.service.EmailService.EmailService;
import com.springbasics.security.service.OTPService.OTPService;
import com.springbasics.security.service.RefreshTokenService.RefreshTokenService;
import com.springbasics.security.service.UserService.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private final JWTHelper jwtHelper;
    private final RefreshTokenService refreshTokenService;

    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final OTPService otpService;
    private final EmailService emailService;

    public AuthenticationServiceImpl(UserService userService, AuthenticationManager authenticationManager, JWTHelper jwtHelper, RefreshTokenService refreshTokenService, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, OTPService otpService, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
        this.userService=userService;
        this.refreshTokenService = refreshTokenService;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<?> signIn(SignInRequest signInRequest) {
        final Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),signInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwtToken=jwtHelper.generateToken(user.getUsername());

        //Generating Refresh Token
        RefreshToken refreshToken=refreshTokenService.createRefreshToken(user.getUserId());
        SignInResponse loginResponse=new SignInResponse(user.getUserId(), user.getUserName(), user.getEmailAddress(),user.getAuthorities(),jwtToken,refreshToken.getToken());
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> signUp(SignUpRequest signUpRequest) {
        OTP otp=new OTP(signUpRequest.getEmailaddress(),signUpRequest.getOtp());
        otpService.verifyOTP(otp);
        //Checking if username or email already exist
        if(userService.isUserNameExist(signUpRequest.getUsername())) throw new CustomException(ErrorCode.USERNAME_ALREADY_EXIST.getCodeValue(),ErrorCode.USERNAME_ALREADY_EXIST.getResponseMessage(),ErrorCode.USERNAME_ALREADY_EXIST.getStatus());
        if(userService.isUserEmailExist(signUpRequest.getEmailaddress())) throw new CustomException(ErrorCode.EMAIL_ALREADY_EXIST.getCodeValue(),ErrorCode.EMAIL_ALREADY_EXIST.getResponseMessage(),ErrorCode.EMAIL_ALREADY_EXIST.getStatus());

        //Creating new user with user role only
        List<Authority> authorityList=new ArrayList<>();
        authorityList.add(authorityRepository.findByroleCode("USER"));
        User user=new User();
        user.setUserName(signUpRequest.getUsername());
        user.setEmailAddress(signUpRequest.getEmailaddress());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setCreatedAt(new Date());
        user.setAuthorities(authorityList);
        user.setAccountStatus(true);
        userService.save(user);
        return ResponseEntity.ok("User Registered successfully!");

    }

    @Override
    public ResponseEntity<?> emailVerification(EmailVerificationRequest emailVerificationRequest) {
        try{
            OTP otp=otpService.createOTP(emailVerificationRequest.getEmail());
            emailService.send(emailVerificationRequest.getEmail(),"Your OTP verification code",otpService.getOtpEmailBody(otp.getOtp()));
            return new ResponseEntity<>(new MessageRespone("OTP Sent Successfully!"),HttpStatus.OK);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            throw new CustomException(ErrorCode.EMAIL_SERVICE_DOWN.getCodeValue(),ErrorCode.EMAIL_SERVICE_DOWN.getResponseMessage());
        }
    }

    @Override
    public ResponseEntity<?> verifyEmailOtp(OTP otp) {
        return null;
    }

    @Override
    public ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        if(!refreshTokenService.refreshTokenExist(refreshTokenRequest.getToken()))  throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND.getCodeValue(),ErrorCode.REFRESH_TOKEN_NOT_FOUND.getResponseMessage());
        RefreshToken refreshToken=refreshTokenService.verifyExpiration(refreshTokenService.findByRefreshToken(refreshTokenRequest.getToken()));
        User user= (User) userService.loadUserByUsername(refreshToken.getUser().getUsername());
        String jwtToken=jwtHelper.generateToken(user.getUsername());
        RefreshToken newRefreshToken=refreshTokenService.createRefreshToken(user.getUserId());
        return new ResponseEntity<>(new RefreshTokenResponse(jwtToken,newRefreshToken.getToken()),HttpStatus.OK);
    }
}
