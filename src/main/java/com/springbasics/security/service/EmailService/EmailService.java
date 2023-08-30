package com.springbasics.security.service.EmailService;

public interface EmailService {
    public Boolean send(String receiversEmail,String subject,String body);
}
