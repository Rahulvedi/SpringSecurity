package com.springbasics.security.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Entity(name = "OTP_MASTER")
@Data
@NoArgsConstructor
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String emailAddress;
    @Column(nullable = false)
    private Long otp;
    @Column(nullable = false)
    private Instant expriryDate;

    public OTP(String emailAddress, Long otp) {
        this.emailAddress = emailAddress;
        this.otp = otp;
    }
}
