package com.springbasics.security.service.OTPService;

import com.springbasics.security.exception.CustomExceptions.CustomException;
import com.springbasics.security.exception.ErrorCode;
import com.springbasics.security.model.OTP;
import com.springbasics.security.repository.OTPRepository;
import com.springbasics.security.service.UtilityService.UtilityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class OTPService {
    @Value("${otp.expires_in}")
    private Long otpDuration;
    OTPRepository otpRepository;
    private final UtilityService utilityService;

    public OTPService(OTPRepository otpRepository, UtilityService utilityService) {
        this.otpRepository = otpRepository;
        this.utilityService = utilityService;
    }

    public OTP createOTP(String email){
        //Deleting Previous OTP for the passed email
        if(otpRepository.existsByEmailAddress(email)) otpRepository.deleteAllByEmailAddress(email);

        //Creating a fresh OTP
        OTP otp=new OTP();
        otp.setEmailAddress(email);
        otp.setExpriryDate(Instant.now().plusMillis(otpDuration));
        otp.setOtp(utilityService.generateSixDigitRandomNumber());
        otpRepository.save(otp);
        return otp;
    }
    public Boolean verifyOTP(OTP otp){
        OTP storedOtp=otpRepository.findByEmailAddress(otp.getEmailAddress()).orElseThrow(()->{throw new CustomException(ErrorCode.EMAIL_NOT_EXIST.getCodeValue(), ErrorCode.EMAIL_NOT_EXIST.getResponseMessage(),ErrorCode.EMAIL_NOT_EXIST.getStatus());});
        if(storedOtp.getExpriryDate().compareTo(Instant.now())>0){
            if(storedOtp.getOtp().equals(otp.getOtp()))  return true;
            throw new CustomException(ErrorCode.OTP_NOT_MATCHED.getCodeValue(), ErrorCode.OTP_EXPIRED.getResponseMessage(),ErrorCode.OTP_NOT_MATCHED.getStatus());
        }
        throw new CustomException(ErrorCode.OTP_EXPIRED.getCodeValue(),ErrorCode.OTP_EXPIRED.getResponseMessage(),ErrorCode.OTP_EXPIRED.getStatus());
    }
    public String getOtpEmailBody(Long otp) {

        String otpEmailBody = """
                Greetings,

                Thank you for registering with our service. To complete your registration, please use the following OTP verification code: [Insert OTP code here]""";
        return  otpEmailBody.replace("[Insert OTP code here]",otp.toString());
    }
}
