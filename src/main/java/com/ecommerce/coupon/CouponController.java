package com.ecommerce.coupon;

import com.ecommerce.coupon.request.CouponCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<CouponDTO> createCoupon(
            @RequestBody CouponCreateRequest request
    ){
        CouponDTO couponDTO = couponService.createCoupon(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(couponDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<CouponDTO> getCoupon(
            @PathVariable Long id
    ){
        CouponDTO couponDTO = couponService.getCouponById(id);
        return ResponseEntity.ok(couponDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<CouponDTO> updateCoupon(
            @PathVariable Long id,
            @RequestBody CouponCreateRequest request
    ){
        CouponDTO couponDTO = couponService.updateCoupon(id, request);
        return ResponseEntity.ok(couponDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CouponDTO> deleteCoupon(
            @PathVariable Long id
    ){
        couponService.deleteCoupon(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<CouponDTO>> getAllCoupons(){
        List<CouponDTO> couponDTOS = couponService.getAllCoupons();
        return ResponseEntity.ok(couponDTOS);
    }
}
