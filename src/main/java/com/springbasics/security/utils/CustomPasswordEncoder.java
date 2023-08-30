package com.springbasics.security.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.stereotype.Service;

@Service
public class CustomPasswordEncoder{
    public String encode(String plainText,String key){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(key); // Use the same encryption key
        encryptor.setConfig(config);
        String encryptedPassword = encryptor.encrypt(plainText);
        System.out.println("Encrypted Text: " + encryptedPassword);
        return encryptedPassword;
    }
    public String decode(String encyptedText,String key){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(key);
        encryptor.setConfig(config);
        String encryptedPassword = encryptor.decrypt(encyptedText);
//        System.out.println("Decrypted Text: " + encryptedPassword);
        return encryptedPassword;
    }
    public static void main(String[] args) {
        CustomPasswordEncoder customPasswordEncoder=new CustomPasswordEncoder();

        System.out.println(customPasswordEncoder.encode("sadmin","encryption_key"));
    }
}
