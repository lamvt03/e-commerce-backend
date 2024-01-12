package com.ecommerce.cart.product;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.ProductService;
import com.ecommerce.product.color.PColorRepository;
import com.ecommerce.product.image.PImageRepository;
import com.ecommerce.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CProductMapper {

    private final PImageRepository pImageRepository;

    private final ProductService productService;

    private final PColorRepository pColorRepository;

    public CProductDTO toDto(CProduct entity){
        Product product = productService.findProductById(entity.getProductId());
        return new CProductDTO(
                entity.getId(),
                product.getId(),
                product.getTitle(),
                product.getDescription(),
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
}
