package com.springbasics.security.service.EmailService;

import com.springbasics.security.exception.CustomExceptions.CustomException;
import com.springbasics.security.exception.ErrorCode;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Boolean send(String receiversEmail, String subject, String body) {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(subject);
            helper.setTo(receiversEmail);
            helper.setText(body);
            javaMailSender.send(message);
            System.out.println("Mail Sent Successfully!");
            return true;
        }catch (Exception exception){
            System.out.println(exception);
            throw  new CustomException(ErrorCode.EMAIL_SERVICE_DOWN.getCodeValue(), ErrorCode.EMAIL_SERVICE_DOWN.getResponseMessage(),ErrorCode.EMAIL_SERVICE_DOWN.getStatus());
        }
    }
}
