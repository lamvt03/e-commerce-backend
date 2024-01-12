package com.ecommerce.product.brand;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.brand.request.PBrandCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PBrandService {

    private final PBrandRepository pBrandRepository;
    private final PBrandMapper pBrandMapper;

    public PBrandDTO createBrand(PBrandCreateRequest request) {
        PBrand pBrand = pBrandMapper.toEntity(request);
        return pBrandMapper.toDto(
                pBrandRepository.save(pBrand)
        );
    }
    private PBrand findPCategoryById(Long id){
        return pBrandRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product brand with id [%s] not found".formatted(id))
                );
    }

    public PBrandDTO updateProductBrand(Long id, PBrandCreateRequest request) {
        PBrand pBrand = findPCategoryById(id);
        return pBrandMapper.toDto(
                pBrandRepository.save(
                        pBrandMapper.toEntity(pBrand, request)
                )
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

    public List<PBrandDTO> getAllProductBrands() {
        return pBrandRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(pBrandMapper::toDto)
                .toList();
    }
}
