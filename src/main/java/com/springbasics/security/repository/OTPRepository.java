package com.springbasics.security.repository;

import com.springbasics.security.model.OTP;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP,Long> {
    Boolean existsByEmailAddress(String email);
    Optional<OTP> findByEmailAddress(String email);
    @Transactional
    void deleteAllByEmailAddress(String email);
}
