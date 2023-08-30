package com.springbasics.security.service.UserService;

import com.springbasics.security.model.Authority;
import com.springbasics.security.model.User;
import com.springbasics.security.repository.UserRepository;
import com.springbasics.security.payload.response.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findUserByUserName(username);
        if(user==null) throw new UsernameNotFoundException("User Not Found with username : "+username);
        return user;
    }
    public UserDetails loadUserByEmail(String emailAddress) throws UsernameNotFoundException{
        User user=userRepository.findUserByEmailAddress(emailAddress);
        if(user==null) throw new UsernameNotFoundException("User Not Found with email : "+emailAddress);
        return user;
    }
    public Boolean isUserNameExist(String username){
        return  userRepository.existsByuserName(username);
    }
    public Boolean isUserEmailExist(String emailAddress){
        return  userRepository.existsByemailAddress(emailAddress);
    }
    public User save(User user){
        return userRepository.save(user);
    }
    public ResponseEntity<?> getUserInfo(Principal user){
        User userObj= (User) this.loadUserByUsername(user.getName());
        UserInfo userInfo=new UserInfo();
        userInfo.setUserId(userObj.getUserId());
        userInfo.setUserName(userObj.getUserName());
        userInfo.setCreatedAt(userObj.getCreatedAt());
        userInfo.setAccountStatus(userObj.isEnabled());
        userInfo.setEmailAddress(userObj.getEmailAddress());
        userInfo.setLastLogin(userObj.getLastLogin());
        userInfo.setAuthorities((List<Authority>) userObj.getAuthorities());
        userInfo.setLastUpdatedAt(userObj.getLastUpdatedAt());
        return ResponseEntity.ok(userInfo);
    }
}
