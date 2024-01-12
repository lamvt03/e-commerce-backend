package com.ecommerce.coupon;

import com.ecommerce.coupon.request.CouponCreateRequest;
import com.ecommerce.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    public CouponDTO createCoupon(CouponCreateRequest request) {
        Coupon coupon = couponRepository.save(
                couponMapper.toEntity(request)
        );
        return couponMapper.toDto(coupon);
    }

    public Coupon findCouponById(Long id){
        return couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon with id [%s] not found".formatted(id)));
    }

    public Coupon findCouponByCode(String code){
        return couponRepository.findByCode(code)
                .filter(coupon -> coupon.getQuantity() > 0)
                .orElseThrow(() -> new ResourceNotFoundException("coupon with code %s not found".formatted(code)));
    }

    public CouponDTO getCouponById(Long id) {
        Coupon coupon = findCouponById(id);
        return couponMapper.toDto(coupon);
    }

    public CouponDTO updateCoupon(Long id, CouponCreateRequest request) {
        Coupon coupon = findCouponById(id);
        coupon.setName(request.name());
        coupon.setDiscount(request.discount());
        coupon.setExpiryAt(
                LocalDateTime.now().plusDays(request.lifeCycle())
                        .withHour(0).withMinute(0).withSecond(0).withNano(0)
        );
        coupon.setQuantity(request.quantity());
        return couponMapper.toDto(
                couponRepository.save(coupon)
        );
    }

    public void deleteCoupon(Long id) {
        Coupon coupon = findCouponById(id);
        couponRepository.delete(coupon);
    }

    public List<CouponDTO> getAllCoupons() {
        return couponRepository.findAll().stream()
                .map(couponMapper::toDto)
                .toList();
    }

    public Coupon save(Coupon coupon){
        return couponRepository.save(coupon);
    }
}
