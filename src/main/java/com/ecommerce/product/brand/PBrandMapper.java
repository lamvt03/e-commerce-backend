package com.ecommerce.product.brand;

import com.ecommerce.product.brand.request.PBrandCreateRequest;
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
                "/" + entity.getCode(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getLastModifiedAt(),
                entity.getLastModifiedBy()
        );
    }
    public PBrand toEntity(PBrandCreateRequest request){
        PBrand entity = new PBrand();
        entity.setName(request.name());
        entity.setCode(slugify.slugify(request.name()));
        return entity;
    }

    public PBrand toEntity(PBrand entity, PBrandCreateRequest request){
        entity.setName(request.name());
        entity.setCode(slugify.slugify(request.name()));
        return entity;
    }
}
