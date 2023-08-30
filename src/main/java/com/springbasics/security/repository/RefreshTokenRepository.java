package com.springbasics.security.repository;

import com.springbasics.security.model.RefreshToken;
import com.springbasics.security.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);
    Boolean existsByToken(String token);
    @Transactional
    int deleteByUser(User user);
}
