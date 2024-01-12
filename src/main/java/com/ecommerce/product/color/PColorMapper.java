package com.ecommerce.product.color;

import com.ecommerce.product.color.request.PColorCreateRequest;
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
    public PColor toEntity(PColorCreateRequest request){
        PColor entity = new PColor();
        entity.setName(request.name());
        entity.setCode(slugify.slugify(request.name()));
        return entity;
    }

    public PColor toEntity(PColor entity, PColorCreateRequest request){
        entity.setName(request.name());
        entity.setCode(slugify.slugify(request.name()));
        return entity;
    }
}
