package com.ecommerce.user.service;

import com.ecommerce.product.ProductMapper;
import com.ecommerce.user.model.User;
import com.ecommerce.user.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class UserMapper {

    private final ProductMapper productMapper;
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
}
