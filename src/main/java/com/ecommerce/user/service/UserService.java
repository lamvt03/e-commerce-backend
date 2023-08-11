package com.ecommerce.user.service;

import com.ecommerce.exception.DuplicateResourceException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.user.UserRepository;
import com.ecommerce.user.model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper;
    private final MailService mailService;

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
    public User findUserByEmail(String email){
        User u = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("The user with email [%s] not exists"
                        .formatted(email)));
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

    public UserDTO changePassword(User authenticatedUser, UserPasswordChange userPasswordChange) {
        return Optional.of(authenticatedUser).stream()
                .filter(u -> passwordEncoder.matches(
                        userPasswordChange.oldPassword(),
                        u.getPassword()
                ))
                .map(u -> {
                    u.setPassword(passwordEncoder.encode(userPasswordChange.newPassword()));
                    return userRepository.save(u);
                })
                .map(userDTOMapper)
                .findFirst()
                .orElseThrow(() -> new BadCredentialsException("Old password wrong"));
    }
    public String handleForgotPassword(String email){
        User u = findUserByEmail(email);
        String otp = createResetPasswordOTP();
        u.setResetPasswordOTP(otp);
        u.setResetPasswordExpired(LocalDateTime.now().plus(5, ChronoUnit.MINUTES));
        userRepository.save(u);
        mailService.sendSimpleMessage(
                email,
                "Reset password request",
                "Your reset password code is %s".formatted(otp)
        );
        return "Please check your email";
    }
    private String createResetPasswordOTP(){
        Random rd = new Random();
        int otpCode = rd.nextInt(900000) + 100000;
        return String.format("%6d", otpCode);
    }

    public String handleResetPassword(UserPasswordReset userPasswordReset){
        User user = userRepository.findByEmail(userPasswordReset.email())
                .filter(u -> u.getResetPasswordExpired().isAfter(LocalDateTime.now()))
                .filter(u -> u.getResetPasswordOTP().equals(userPasswordReset.otp()))
                .map(u -> {
                    u.setPassword(passwordEncoder.encode(userPasswordReset.newPassword()));
                    u.setResetPasswordOTP(null);
                    u.setResetPasswordExpired(null);
                    return userRepository.save(u);
                })
                .orElseThrow(() -> new RuntimeException("Invalid reset password token"));

        if (Optional.ofNullable(user).isPresent()){
            return "Reset password successfully";
        }
        return "failure";
    }
}
