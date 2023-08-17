package com.ecommerce.product.category;

import com.ecommerce.product.category.PCategory;
import com.ecommerce.product.category.PCategoryDTO;
import org.springframework.stereotype.Service;

@Service
public class PCategoryMapper {

    public PCategoryDTO toDto(PCategory entity){
        return new PCategoryDTO(
                entity.getTitle(),
                entity.getLastModifiedAt()
        );
    }
    public PCategory toEntity(PCategoryDTO dto){
        PCategory entity = new PCategory();
        entity.setTitle(dto.title());
        return entity;
    }
}
