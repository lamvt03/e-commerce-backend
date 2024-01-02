package com.ecommerce.coupon;

import com.ecommerce.coupon.request.CouponCreateRequest;
import com.ecommerce.util.RandomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CouponMapper {

    private final RandomService randomService;

    public CouponDTO toDto(Coupon entity){
        return new CouponDTO(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getQuantity(),
                entity.getExpiryAt(),
                entity.getDiscount(),
                entity.getCreatedAt(),
                entity.getLastModifiedAt()
        );
    }

    public Coupon toEntity(CouponCreateRequest request) {
        int COUPON_CODE_LENGTH = 12;
        String couponCode = randomService.randomCouponCode(COUPON_CODE_LENGTH);

        LocalDateTime expiryAt = LocalDateTime.now().plusDays(request.lifeCycle())
                .withHour(0).withMinute(0).withSecond(0).withNano(0);

        return new Coupon(
                request.name(),
                couponCode,
                expiryAt,
                request.discount(),
                request.quantity()
        );
    }
}
