package com.ecommerce.product.brand;

import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PBrandMapper {

    private final Slugify slugify;

    public PBrandDTO toDto(PBrand entity){
        return new PBrandDTO(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getLastModifiedAt(),
                entity.getLastModifiedBy()
        );
    }
    public PBrand toEntity(PBrandDTO dto){
        PBrand entity = new PBrand();
        entity.setName(dto.name());
        entity.setCode(slugify.slugify(dto.name()));
        return entity;
    }

    public PBrand toEntity(PBrand entity, PBrandDTO dto){
        entity.setName(dto.name());
        entity.setCode(slugify.slugify(dto.name()));
        return entity;
    }
}
