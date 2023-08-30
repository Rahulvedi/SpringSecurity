package com.springbasics.security.repository;

import com.springbasics.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUserName(String userName);
    User findUserByUserId(Long userId);
    User findUserByEmailAddress(String emailAddress);
    Boolean existsByuserName(String username);
    Boolean existsByemailAddress(String email);

}
