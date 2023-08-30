package com.springbasics.security.service.UtilityService;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UtilityService {
    public Long generateSixDigitRandomNumber(){
        Random random = new Random();
        return random.nextLong(900000) + 100000;
    }
}
