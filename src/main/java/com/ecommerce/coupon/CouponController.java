package com.ecommerce.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<?> createCoupon(
            @RequestBody CouponCreateRequest request
    ){
        return ResponseEntity.ok(
                couponService.createCoupon(request)
        );
    }
}
