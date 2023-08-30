package com.springbasics.security.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity(name = "REFRESH_TOKEN_MASTER")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    @Column(nullable = false,unique = true)
    private String token;
    @Column(nullable = false)
    private Instant expriryDate;
}
