package com.ecommerce.user.service;

import com.ecommerce.product.ProductMapper;
import com.ecommerce.user.model.User;
import com.ecommerce.user.model.UserDTO;
import com.ecommerce.user.model.request.UserRegistration;
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
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getMobile(),
                entity.getWishlist().stream()
                        .map(productMapper::toDto)
                        .collect(Collectors.toSet())
        );
    }
    public User toEntity(UserRegistration dto){
        User entity = new User();
        entity.setEmail(dto.email());
        entity.setPassword(passwordEncoder.encode(dto.password()));
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setMobile(dto.mobile());
        return entity;
    }
}
