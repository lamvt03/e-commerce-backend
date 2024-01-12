package com.ecommerce.product.category;

import com.ecommerce.product.category.request.PCategoryCreateRequest;
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
    public PCategory toEntity(PCategoryCreateRequest request){
        PCategory entity = new PCategory();
        entity.setName(request.name());
        entity.setCode(slugify.slugify(request.name()));
        return entity;
    }

    public PCategory toEntity(PCategory entity, PCategoryCreateRequest request){
        entity.setName(request.name());
        entity.setCode(slugify.slugify(request.name()));
        return entity;
    }
}
