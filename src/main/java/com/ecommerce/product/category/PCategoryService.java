package com.ecommerce.product.category;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.category.request.PCategoryCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PCategoryService {

    private final PCategoryRepository pCategoryRepository;
    private final PCategoryMapper pCategoryMapper;

    public PCategoryDTO createCategory(PCategoryCreateRequest request) {
        PCategory pCategory = pCategoryMapper.toEntity(request);
        return pCategoryMapper.toDto(
                pCategoryRepository.save(pCategory)
        );
    }
    private PCategory findPCategoryById(Long id){
        return pCategoryRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product category with id [%s] not found".formatted(id))
                );
    }

    public PCategoryDTO updateProductCategory(Long id, PCategoryCreateRequest request) {
        PCategory pCategory = findPCategoryById(id);
        return pCategoryMapper.toDto(
                pCategoryRepository.save(pCategoryMapper.toEntity(pCategory, request))
        );
    }

    public void deleteProductCategory(Long id) {
        PCategory pCategory = findPCategoryById(id);
        pCategoryRepository.delete(pCategory);
    }

    public PCategoryDTO getProductCategory(Long id) {
        PCategory pCategory = findPCategoryById(id);
        return pCategoryMapper.toDto(pCategory);
    }

    public List<PCategoryDTO> getAllProductCategories(){
        return pCategoryRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(pCategoryMapper::toDto)
                .toList();
    }
}
