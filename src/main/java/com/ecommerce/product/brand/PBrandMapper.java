package com.ecommerce.product.brand;

import org.springframework.stereotype.Service;

@Service
public class PBrandMapper {

    public PBrandDTO toDto(PBrand entity){
        return new PBrandDTO(
                entity.getName(),
                entity.getLastModifiedAt()
        );
    }
    public PBrand toEntity(PBrandDTO dto){
        PBrand entity = new PBrand();
        entity.setName(dto.name());
        return entity;
    }
}
