package com.ecommerce.user.service;

import com.ecommerce.exception.DuplicateResourceException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.user.UserRepository;
import com.ecommerce.user.model.User;
import com.ecommerce.user.model.UserDTO;
import com.ecommerce.user.model.UserRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper;

    public UserDTO registerUser(UserRegistration u){
        if(!userRepository.existsByEmail(u.email())){
            User newUser = new User();
            newUser.setEmail(u.email());
            newUser.setPassword(passwordEncoder.encode(u.password()));
            newUser.setFirstName(u.firstName());
            newUser.setLastName(u.lastName());
            newUser.setMobile(u.mobile());
            newUser = userRepository.save(newUser);
            return userDTOMapper.apply(newUser);
        }else
            throw new DuplicateResourceException("The user with email [%s] already exists".formatted(u.email()));
    }

    private User findUserById(Long id){
        User u = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The user with id [%s] not exists"
                        .formatted(id)));
        return u;
    }
    public UserDTO getUser(Long id){
        User u = findUserById(id);
        return userDTOMapper.apply(u);
    }
    public UserDTO deleteUser(Long id){
        User u = findUserById(id);
        u.setEnable(false);
        return userDTOMapper.apply(userRepository.save(u));
    }
    public String lockUserWithId(Long id){
        User u = findUserById(id);
        u.setNonLocked(false);
        userRepository.save(u);
        return "The user with id [%s] was locked".formatted(id);
    }
    public String unlockUserWithId(Long id){
        User u = findUserById(id);
        u.setNonLocked(true);
        userRepository.save(u);
        return "The user with id [%s] was unlocked".formatted(id);
    }
}
