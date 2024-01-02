package com.ecommerce.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class RandomService {

    private final SecureRandom random;

    public String randomOtpValue(int length) {
        final String DIGITS = "0123456789";
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(DIGITS.length());
            char randomDigit = DIGITS.charAt(randomIndex);
            sb.append(randomDigit);
        }

        return sb.toString();
    }
    public String randomCouponCode(int length){
        final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(LETTERS.length());
            char randomLetter = LETTERS.charAt(randomIndex);
            sb.append(randomLetter);
        }

        return sb.toString();
    }
}
