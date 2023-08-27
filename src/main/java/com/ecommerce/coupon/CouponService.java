package com.ecommerce.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    public CouponDTO createCoupon(CouponCreateRequest request) {
        return couponMapper.toDto(
                couponRepository.save(
                        new Coupon(
                                request.name(),
                                LocalDateTime.now().plusDays(request.lifeCycle()),
                                request.discount()
                        )
                )
        );
    }
}
