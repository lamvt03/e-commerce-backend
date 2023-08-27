package com.ecommerce.product.brand;

import com.ecommerce.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PBrandService {

    private final PBrandRepository pBrandRepository;
    private final PBrandMapper pBrandMapper;

    public PBrandDTO createBrand(PBrandDTO pBrandDTO) {
        PBrand pBrand = pBrandMapper.toEntity(pBrandDTO);
        return pBrandMapper.toDto(
                pBrandRepository.save(pBrand)
        );
    }
    private PBrand findPCategoryById(Long id){
        PBrand pBrand = pBrandRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product brand with id [%s] not found".formatted(id))
                );
        return pBrand;
    }

    public PBrandDTO updateProductBrand(Long id, PBrandDTO pBrandDTO) {
        PBrand pBrand = findPCategoryById(id);
        pBrand.setName(pBrandDTO.name());
        return pBrandMapper.toDto(
                pBrandRepository.save(pBrand)
        );
    }

    public void deleteProductBrand(Long id) {
        PBrand pBrand = findPCategoryById(id);
        pBrandRepository.delete(pBrand);
    }

    public PBrandDTO getProductBrand(Long id) {
        PBrand pBrand = findPCategoryById(id);
        return pBrandMapper.toDto(pBrand);
    }

}
