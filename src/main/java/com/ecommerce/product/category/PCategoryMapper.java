package com.ecommerce.product.category;

import com.ecommerce.product.category.PCategory;
import com.ecommerce.product.category.PCategoryDTO;
import org.springframework.stereotype.Service;

@Service
public class PCategoryMapper {

    public PCategoryDTO toDto(PCategory entity){
        return new PCategoryDTO(
                entity.getName(),
                entity.getLastModifiedAt()
        );
    }
    public PCategory toEntity(PCategoryDTO dto){
        PCategory entity = new PCategory();
        entity.setName(dto.name());
        return entity;
    }
}
