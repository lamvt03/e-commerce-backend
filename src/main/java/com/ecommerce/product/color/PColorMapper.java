package com.ecommerce.product.color;

import com.ecommerce.product.brand.PBrand;
import com.ecommerce.product.brand.PBrandDTO;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PColorMapper {

    private final Slugify slugify;

    public PColorDTO toDto(PColor entity){
        return new PColorDTO(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getLastModifiedAt(),
                entity.getLastModifiedBy()
        );
    }
    public PColor toEntity(PColorDTO dto){
        PColor entity = new PColor();
        entity.setName(dto.name());
        entity.setCode(slugify.slugify(dto.name()));
        return entity;
    }

    public PColor toEntity(PColor entity, PColorDTO dto){
        entity.setName(dto.name());
        entity.setCode(slugify.slugify(dto.name()));
        return entity;
    }
}
