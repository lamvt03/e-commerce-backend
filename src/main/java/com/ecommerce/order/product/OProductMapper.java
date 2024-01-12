package com.ecommerce.order.product;

import com.ecommerce.cart.product.CProduct;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.ProductService;
import com.ecommerce.product.color.PColorRepository;
import com.ecommerce.product.image.PImageRepository;
import com.ecommerce.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OProductMapper {

    private final PImageRepository pImageRepository;

    private final ProductService productService;

    private final PColorRepository pColorRepository;
    public OProductDTO toDto(OProduct entity){
        Product product = productService.findProductById(entity.getProductId());
        return new OProductDTO(
                entity.getId(),
                product.getId(),
                product.getTitle(),
                pColorRepository.findById(entity.getColorId())
                        .orElseThrow()
                        .getName(),
                entity.getPrice(),
                entity.getQuantity(),
                pImageRepository.findFirstByProduct_Id(product.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("The product image not found"))
                        .getUrl()
        );
    }
    public OProduct toEntity(CProduct cProduct, Long orderId){
        return OProduct.builder()
                .productId(cProduct.getProductId())
                .colorId(cProduct.getColorId())
                .quantity(cProduct.getQuantity())
                .price(cProduct.getPrice())
                .orderId(orderId)
                .build();
    }
}
