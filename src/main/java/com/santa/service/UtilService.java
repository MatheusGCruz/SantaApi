package com.santa.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class UtilService {

    public String generatePinCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder pinCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            pinCode.append(chars.charAt(random.nextInt(chars.length())));
        }
        return pinCode.toString();
    }
}
