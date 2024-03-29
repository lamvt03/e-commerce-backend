package com.ecommerce.user.service;

import com.ecommerce.product.ProductMapper;
import com.ecommerce.user.model.User;
import com.ecommerce.user.model.UserDTO;
import com.ecommerce.user.model.UserRole;
import com.ecommerce.user.model.request.UserRegistration;
import com.ecommerce.user.model.resp.UserRegistrationResp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class UserMapper {

    private final ProductMapper productMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDTO toDto(User entity) {
        return new UserDTO(
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getMobile(),
                entity.getWishlist().stream()
                        .map(productMapper::toDto)
                        .collect(Collectors.toSet())
        );
    }

    public User toEntity(UserRegistration dto, boolean isUser){
        UserRole role = isUser ? UserRole.USER : UserRole.ADMIN;
        User entity = new User();
        entity.setEmail(dto.email());
        entity.setPassword(passwordEncoder.encode(dto.password()));
        entity.setRole(role);
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setMobile(dto.mobile());
        return entity;
    }

    public UserRegistrationResp toRegistrationResponse(User entity){
        return new UserRegistrationResp(
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getMobile()
        );
    }
}
