package com.ecommerce.config;

import com.github.slugify.Slugify;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;

@Configuration
public class CdiConfig {

    @Bean
    public SecureRandom secureRandom(){
        return new SecureRandom();
    }

    @Bean
    public Slugify slugify(){
        return Slugify.builder()
                .lowerCase(true)
                .transliterator(true)
                .build();
    }
}
