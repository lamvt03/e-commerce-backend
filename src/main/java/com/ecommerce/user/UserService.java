package com.ecommerce.user;

import com.ecommerce.exception.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(User u){
        if(!userRepository.existsByEmail(u.getEmail())){
            User newUser = new User();
            newUser.setEmail(u.getEmail());
//            newUser.setPass(u.getPass());
            newUser.setPass(passwordEncoder.encode(u.getPass()));
            newUser.setFirstName(u.getFirstName());
            newUser.setLastName(u.getLastName());
            newUser.setMobile(u.getMobile());

            return userRepository.save(newUser);
        }else
            throw new DuplicateResourceException("The user with email [%s] already exists".formatted(u.getEmail()));
    }
}
