package com.ecommerce.cart;

import com.ecommerce.cart.product.CProduct;
import com.ecommerce.cart.product.CProductRepository;
import com.ecommerce.cart.request.CartProductRequest;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.ProductService;
import com.ecommerce.product.model.Product;
import com.ecommerce.util.PaginationService;
import com.ecommerce.util.model.PaginationDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    private final CProductRepository cProductRepository;

    private final ProductService productService;

    private final PaginationService paginationService;

    public CartDTO addCart(Long userId, CartProductRequest request) {
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(EntityNotFoundException::new);

        CProduct cProduct = cProductRepository.findByCart_IdAndProductIdAndColorIdOrderByCreatedAtDesc(cart.getId(), request.productId(), request.colorId())
                .orElse(null);
        if(cProduct != null){
            cProduct.setQuantity(request.quantity());
        }else{
            Product p = productService.findProductById(request.productId());
            cProduct = CProduct.builder()
                    .cart(cart)
                    .productId(request.productId())
                    .colorId(request.colorId())
                    .quantity(request.quantity())
                    .price(p.getPrice())
                    .build();
        }
        cProductRepository.save(cProduct);

        double total = cProductRepository.findAllByCart_Id(cart.getId()).stream()
                .map(CProduct::getPrice)
                .reduce((double) 0, Double::sum);

        cart.setTotal(total);
        cart.setTotalAfterDiscount(total);
        return cartMapper.toDto(
                cart, paginationService.getDefaultPaginationDTO()
        );
    }

    public CartDTO getCart(Long userId, PaginationDTO paginationDTO) {
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with user id [%s] not found".formatted(userId)));
        return cartMapper.toDto(
                cart, paginationDTO
        );
    }

    public CartDTO deleteCartProduct(Long userId, Long productId) {
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(EntityNotFoundException::new);
        cProductRepository.deleteById(productId);
        return cartMapper.toDto(cart, paginationService.getDefaultPaginationDTO());
    }
}
