package com.ecommerce.coupon;

import com.ecommerce.coupon.request.CouponCreateRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CouponMapper {

    public CouponDTO toDto(Coupon entity){
        return new CouponDTO(
                entity.getId(),
                entity.getName(),
                entity.getExpiryAt(),
                entity.getDiscount(),
                entity.getCreatedAt(),
                entity.getLastModifiedAt()
        );
    }

    public Coupon toEntity(CouponCreateRequest request) {
        LocalDateTime expiryAt = LocalDateTime.now().plusDays(request.lifeCycle())
                .withHour(0).withMinute(0).withSecond(0).withNano(0);

        return new Coupon(
                request.name(),
                expiryAt,
                request.discount(),
                request.quantity()
        );
    }
}
