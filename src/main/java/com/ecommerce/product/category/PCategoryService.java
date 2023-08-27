package com.ecommerce.product.category;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.category.PCategory;
import com.ecommerce.product.category.PCategoryDTO;
import com.ecommerce.product.category.PCategoryMapper;
import com.ecommerce.product.category.PCategoryRepository;
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
        PCategory pCategory = pCategoryRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product category with id [%s] not found".formatted(id))
                );
        return pCategory;
    }

    public PCategoryDTO updateProductCategory(Long id, PCategoryDTO pCategoryDTO) {
        PCategory pCategory = findPCategoryById(id);
        pCategory.setName(pCategoryDTO.name());
        return pCategoryMapper.toDto(pCategoryRepository.save(pCategory));
    }

    public void deleteProductCategory(Long id) {
        PCategory pCategory = findPCategoryById(id);
        pCategoryRepository.delete(pCategory);
    }

    public PCategoryDTO getProductCategory(Long id) {
        PCategory pCategory = findPCategoryById(id);
        return pCategoryMapper.toDto(pCategory);
    }

}
