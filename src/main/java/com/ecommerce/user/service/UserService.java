package com.ecommerce.user.service;

import com.ecommerce.cart.*;
import com.ecommerce.cart.product.CProductRepository;
import com.ecommerce.coupon.Coupon;
import com.ecommerce.coupon.CouponApplyRequest;
import com.ecommerce.coupon.CouponRepository;
import com.ecommerce.exception.DuplicateResourceException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.order.*;
import com.ecommerce.order.orderPaymentIntent.OrderPaymentIntent;
import com.ecommerce.order.orderProduct.OrderProduct;
import com.ecommerce.product.ProductMapper;
import com.ecommerce.product.ProductRepository;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.model.ProductDTO;
import com.ecommerce.user.UserRepository;
import com.ecommerce.user.model.*;
import com.ecommerce.user.model.request.*;
import com.ecommerce.user.otp.Otp;
import com.ecommerce.user.otp.OtpRepository;
import com.ecommerce.util.RandomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public UserDTO registerUser(UserRegistration userRegistration){
        if(!userRepository.existsByEmail(userRegistration.email())){
            User user = userRepository.save(
                    userMapper.toEntity(userRegistration)
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

    private final CouponRepository couponRepository;
//    public CartDTO applyCoupon(Long userId, CouponApplyRequest request) {
//        Coupon coupon = couponRepository.findByName(request.name())
//                .orElseThrow(
//                        () -> new ResourceNotFoundException("coupon with name [%s] not exists".formatted(request.name()))
//                );
//        User user = findUserById(userId);
//        Cart cart = user.getCart();
//        double totalAfterDiscount = cart.getTotal() * (1 - coupon.getDiscount() / 100.0);
//        cart.setTotalAfterDiscount(totalAfterDiscount);
//
//        return cartMapper.toDto(
//                cartRepository.save(cart)
//        );
//    }

//    public Object createOrder(Long userId, OrderCreateRequest request) {
//        if(!request.COD())
//            throw new RuntimeException("Unsupported payment method");
//        User user = findUserById(userId);
//        Cart userCart = user.getCart();
//        double finalAmount = 0;
//        if(request.couponApplied())
//            finalAmount += userCart.getTotalAfterDiscount();
//        else
//            finalAmount += userCart.getTotal();
//
//        Order newOrder = new Order(
//                userCart.getProducts().stream()
//                        .map(cartProduct -> new OrderProduct(
//                                null,
//                                cartProduct.getProduct(),
//                                cartProduct.getCount(),
//                                cartProduct.getColor()
//                        ))
//                        .collect(Collectors.toSet()),
//                OrderStatus.CASH_ON_DELIVERY,
//                new OrderPaymentIntent(
//                        "COD",
//                        finalAmount,
//                        OrderStatus.CASH_ON_DELIVERY,
//                        "usd"
//                ),
//                user
//        );

//        userCart.getProducts().forEach(cartProduct -> {
//            Product product = productRepository.findById(cartProduct.getId()).orElseThrow();
//            product.setQuantity(product.getQuantity() - cartProduct.getCount());
//            product.setSold(product.getSold() + cartProduct.getCount());
//            productRepository.save(product);
//        });
//
//        return orderMapper.toDto(
//                orderRepository.save(
//                        newOrder
//                )
//        );
//    }

    public Object getOrders(Long userId) {
        return orderRepository.findAllByOrderBy_Id(userId).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toSet());
    }
}
