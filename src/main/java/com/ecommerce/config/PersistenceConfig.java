package com.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getCurrentAuditor")
public class PersistenceConfig {

    @Bean
    AuditorAware<String> getCurrentAuditor(){
        return () -> {
            Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
            if(loggedUser != null)
                return Optional.of(loggedUser.getName());
            return Optional.of("");
        };
    }
}
