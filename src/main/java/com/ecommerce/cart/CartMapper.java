package com.ecommerce.cart;

import com.ecommerce.cart.product.CProductMapper;
import com.ecommerce.cart.product.CProductRepository;
import com.ecommerce.coupon.Coupon;
import com.ecommerce.coupon.CouponRepository;
import com.ecommerce.util.PaginationService;
import com.ecommerce.util.model.PaginationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartMapper {

    private final CProductRepository cProductRepository;
    private final CProductMapper cProductMapper;
    private final CouponRepository couponRepository;

    private final PaginationService paginationService;

    public CartDTO toDto(Cart entity, PaginationDTO paginationDTO){
        AtomicReference<String> couponCodeRf = new AtomicReference<>("");
        AtomicReference<String> couponNameRf = new AtomicReference<>("");
        if(entity.getCouponId() != null){
            couponRepository.findById(entity.getCouponId())
                    .ifPresent(c -> {
                        couponCodeRf.set(c.getCode());
                        couponNameRf.set(c.getName());
                    });
        }
        Pageable pageable = paginationService.getPageable(paginationDTO);
        return new CartDTO(
                cProductRepository.findAllByCart_Id(entity.getId(), pageable).stream()
                        .map(cProductMapper::toDto)
                        .collect(Collectors.toSet()),
                couponCodeRf.get(),
                couponNameRf.get(),
                entity.getTotal(),
                entity.getTotalAfterDiscount()
        );
    }
}
