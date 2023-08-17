package com.ecommerce.product.service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.model.entity.PCategory;
import com.ecommerce.product.model.dto.PCategoryDTO;
import com.ecommerce.product.repository.PCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PCategoryService {

    private final PCategoryRepository pCategoryRepository;
    private final PCategoryMapper pCategoryMapper;

    public PCategoryDTO createCategory(PCategoryDTO pCategoryDTO) {
        PCategory pCategory = pCategoryMapper.toEntity(pCategoryDTO);
        return pCategoryMapper.toDto(pCategoryRepository.save(pCategory));
    }
    private PCategory findPCategoryById(Long id){
        PCategory pCategory = pCategoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product category with id [%s] not found".formatted(id))
                );
        return pCategory;
    }

    public PCategoryDTO updateProductCategory(Long id, PCategoryDTO pCategoryDTO) {
        PCategory pCategory = findPCategoryById(id);
        pCategory.setTitle(pCategoryDTO.title());
        return pCategoryMapper.toDto(pCategoryRepository.save(pCategory));
    }

    public void deleteProductCategory(Long id) {
        PCategory pCategory = findPCategoryById(id);
        pCategory.setDeleted(true);
        pCategoryRepository.save(pCategory);
    }

    public PCategoryDTO getProductCategory(Long id) {
        PCategory pCategory = findPCategoryById(id);
        return pCategoryMapper.toDto(pCategory);
    }

    public PCategoryDTO restoreCategory(Long id) {
        PCategory deletedPCategory = pCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found"));
        deletedPCategory.setDeleted(false);
        return pCategoryMapper.toDto(pCategoryRepository.save(deletedPCategory));
    }
}
