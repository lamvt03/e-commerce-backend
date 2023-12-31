package com.ecommerce.cart;

import com.ecommerce.cart.product.CProductMapper;
import com.ecommerce.cart.product.CProductRepository;
import com.ecommerce.util.PaginationService;
import com.ecommerce.util.model.PaginationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartMapper {

    private final CProductRepository cProductRepository;
    private final CProductMapper cProductMapper;

    private final PaginationService paginationService;

    public CartDTO toDto(Cart entity, PaginationDTO paginationDTO){
        Pageable pageable = paginationService.getPageable(paginationDTO);
        return new CartDTO(
                cProductRepository.findAllByCart_Id(entity.getId(), pageable).stream()
                        .map(cProductMapper::toDto)
                        .collect(Collectors.toSet()),
                entity.getTotal(),
                entity.getTotalAfterDiscount()
        );
    }
}
