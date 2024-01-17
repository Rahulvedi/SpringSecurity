package com.springbasics.security.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Table(name = "USER_MASTER")
@Entity
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long userId;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @Column(name = "USER_KEY")
    private String password;
    @Column(name = "CREATED_AT")
    private Date createdAt;
    @Column(name = "LAST_UPDATED_AT")
    private Date lastUpdatedAt;
    @Column(name = "LAST_LOGIN")
    private Date lastLogin;
    @Column(name = "ACCOUNT_STATUS")
    private boolean accountStatus;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE_MASTER",joinColumns = @JoinColumn(referencedColumnName = "ID"),inverseJoinColumns = @JoinColumn(referencedColumnName = "ROLE_ID"))
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Authority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountStatus;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountStatus;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.accountStatus;
    }

    @Override
    public boolean isEnabled() {
        return this.accountStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}
