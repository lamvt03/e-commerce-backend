package com.ecommerce.user.service;

import com.ecommerce.cart.*;
import com.ecommerce.coupon.CouponRepository;
import com.ecommerce.exception.DuplicateResourceException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.order.*;
import com.ecommerce.product.ProductMapper;
import com.ecommerce.product.model.ProductDTO;
import com.ecommerce.user.UserRepository;
import com.ecommerce.user.model.*;
import com.ecommerce.user.model.request.*;
import com.ecommerce.user.otp.Otp;
import com.ecommerce.user.otp.OtpRepository;
import com.ecommerce.util.MailService;
import com.ecommerce.util.PaginationService;
import com.ecommerce.util.RandomService;
import com.ecommerce.util.model.PaginationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;

    private final ProductMapper productMapper;

    private final CartRepository cartRepository;
    private final RandomService randomService;
    private final OtpRepository otpRepository;

    private final PaginationService paginationService;

    public UserDTO registerUser(UserRegistration userRegistration){
        if(!userRepository.existsByEmail(userRegistration.email())){
            User user = userRepository.save(
                    userMapper.toEntity(userRegistration, true)
            );
            cartRepository.save(
                    Cart.builder()
                    .user(user)
                    .build()
            );
            return userMapper.toDto(user);
        }else
            throw new DuplicateResourceException("The user with email [%s] already exists".formatted(userRegistration.email()));
    }

    public User findUserById(Long id){
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The user with id [%s] not exists"
                        .formatted(id)));
    }
    public User saveUser(User user){
        return userRepository.save(user);
    }
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("The user with email [%s] not exists"
                        .formatted(email)));
    }
    public UserDTO getUser(Long id){
        User user = findUserById(id);
        return userMapper.toDto(user);
    }
    public void deleteUser(Long id){
        User user = findUserById(id);
        user.setEnable(false);
        userMapper.toDto(userRepository.save(user));
    }
    public void activateOrDeactivateUser(Long userId){
        User u = userRepository.findByIdAndRole(userId, UserRole.USER)
                .orElseThrow(() -> new ResourceNotFoundException("User with id [%s] not found".formatted(userId)));

        u.setEnable(!u.isEnabled());
        userRepository.save(u);
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
                .map(userMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new BadCredentialsException("Old password wrong"));
    }
    public void handleForgotPassword(UserPasswordForgot userPasswordForgot){
        User u = findUserByEmail(userPasswordForgot.email());
        String otpCode = randomService.randomOtpValue(6);
        Otp userOtp = Otp.builder()
                .code(otpCode)
                .expiredAt(LocalDateTime.now().plusMinutes(5))
                .user(u)
                .build();
        otpRepository.save(userOtp);

        mailService.sendSimpleMessage(
                userPasswordForgot.email(),
                "Reset password request",
                "Your reset password code is %s".formatted(otpCode)
        );
    }

    public void handleResetPassword(UserPasswordReset userPasswordReset){
        User user = findUserByEmail(userPasswordReset.email());
        Otp otp = otpRepository.findByUser_Id(user.getId())
                .filter(o -> o.getExpiredAt().isAfter(LocalDateTime.now()))
                .filter(o -> o.getCode().equals(userPasswordReset.otpCode()))
                .orElseThrow(() -> new RuntimeException("Invalid reset password token"));
        user.setPassword(passwordEncoder.encode(userPasswordReset.newPassword()));
        userRepository.save(user);
        otpRepository.delete(otp);
    }

    public Set<ProductDTO> getWishlist(Long id) {
        User user = findUserById(id);
        return user.getWishlist().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toSet());
    }

    public List<User> getActivateUserList(PaginationDTO paginationDTO){
        Pageable pageable = paginationService.getPageable(paginationDTO);
        return userRepository.findByRoleAndIsEnable(UserRole.USER, true, pageable);
    }
    public List<User> getDeactivateUserList(PaginationDTO paginationDTO){
        Pageable pageable = paginationService.getPageable(paginationDTO);
        return userRepository.findByRoleAndIsEnable(UserRole.USER, false, pageable);
    }

    public List<User> getAdmins(PaginationDTO paginationDTO) {
        Pageable pageable = paginationService.getPageable(paginationDTO);
        return userRepository.findByRole(UserRole.ADMIN, pageable);
    }

    public User createAdmin(UserRegistration request) {
        User admin = userMapper.toEntity(request, false);
        return userRepository.save(admin);
    }

    public void activateOrDeactivateAdmin(Long userId) {
        User admin = userRepository.findByIdAndRole(userId, UserRole.ADMIN)
                .orElseThrow(() -> new ResourceNotFoundException("Admin with id [%s] not found".formatted(userId)));

        admin.setEnable(!admin.isEnabled());
        userRepository.save(admin);
    }
}
