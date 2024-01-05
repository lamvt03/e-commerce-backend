package com.ecommerce.product.category;

import com.ecommerce.product.brand.PBrand;
import com.ecommerce.product.brand.PBrandDTO;
import com.ecommerce.product.category.PCategory;
import com.ecommerce.product.category.PCategoryDTO;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PCategoryMapper {

    private final Slugify slugify;

    public PCategoryDTO toDto(PCategory entity){
        return new PCategoryDTO(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                "/" + entity.getCode(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getLastModifiedAt(),
                entity.getLastModifiedBy()
        );
    }
    public PCategory toEntity(PCategoryDTO dto){
        PCategory entity = new PCategory();
        entity.setName(dto.name());
        entity.setCode(slugify.slugify(dto.name()));
        return entity;
    }

    public PCategory toEntity(PCategory entity, PCategoryDTO dto){
        entity.setName(dto.name());
        entity.setCode(slugify.slugify(dto.name()));
        return entity;
    }
}
