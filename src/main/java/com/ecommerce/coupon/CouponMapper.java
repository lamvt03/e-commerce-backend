package com.ecommerce.coupon;

import org.springframework.stereotype.Service;

@Service
public class CouponMapper {

    public CouponDTO toDto(Coupon entity){
        return new CouponDTO(
                entity.getName(),
                entity.getExpiryAt(),
                entity.getDiscount(),
                entity.getCreatedAt()
        );
    }
}
