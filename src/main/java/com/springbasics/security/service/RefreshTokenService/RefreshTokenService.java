package com.springbasics.security.service.RefreshTokenService;

import com.springbasics.security.exception.CustomExceptions.CustomException;
import com.springbasics.security.exception.ErrorCode;
import com.springbasics.security.model.RefreshToken;
import com.springbasics.security.model.User;
import com.springbasics.security.repository.RefreshTokenRepository;
import com.springbasics.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
@Service
public class RefreshTokenService {
    @Value("${refreshToken.expires_in}")
    private Long refreshTokenDuration;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(Long userId){
        //Deleting Previous Refresh Tokens
        User user=userRepository.findUserByUserId(userId);
        deleteByUserId(userId);
        //Creating and Saving new Refresh Token
        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpriryDate(Instant.now().plusMillis(refreshTokenDuration));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken=refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }
    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpriryDate().compareTo(Instant.now())<0){
            throw new CustomException(Boolean.FALSE,ErrorCode.REFRESH_TOKEN_EXPIRED.getCodeValue(),ErrorCode.REFRESH_TOKEN_EXPIRED.getResponseMessage(),ErrorCode.REFRESH_TOKEN_EXPIRED.getStatus());
        }
        return token;
    }
    @Transactional
    public  int deleteByUserId(Long userId){
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
    public Boolean refreshTokenExist(String token){
        return refreshTokenRepository.existsByToken(token);
    }
    public RefreshToken findByRefreshToken(String token){
        return refreshTokenRepository.findByToken(token).get();
    }
}
