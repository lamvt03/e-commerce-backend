package com.ecommerce.product.service;

import com.ecommerce.product.model.entity.PCategory;
import com.ecommerce.product.model.dto.PCategoryDTO;
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
