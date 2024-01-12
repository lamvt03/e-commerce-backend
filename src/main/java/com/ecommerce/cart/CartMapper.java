package com.ecommerce.cart;

import com.ecommerce.cart.model.Cart;
import com.ecommerce.cart.model.CartCoupon;
import com.ecommerce.cart.model.CartDTO;
import com.ecommerce.cart.product.CProductMapper;
import com.ecommerce.cart.product.CProductRepository;
import com.ecommerce.coupon.CouponRepository;
import com.ecommerce.util.PaginationService;
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

    public CartDTO toDto(Cart entity){
        AtomicReference<CartCoupon> couponRf = new AtomicReference<>(null);

        if(entity.getCouponId() != null){
            couponRepository.findById(entity.getCouponId())
                    .ifPresent(c -> {
                        couponRf.set(new CartCoupon(
                                c.getCode(),
                                c.getName(),
                                c.getDiscount() + "%"
                        ));
                    });
        }

        Pageable pageable = paginationService.getDefaultPageable();
        return new CartDTO(
                cProductRepository.findAllByCart_Id(entity.getId(), pageable).stream()
                        .map(cProductMapper::toDto)
                        .collect(Collectors.toSet()),
                couponRf.get(),
                entity.getTotal(),
                entity.getTotalAfterDiscount()
        );
    }
}
