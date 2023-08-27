package com.ecommerce.product.color;

import org.springframework.stereotype.Service;

@Service
public class PColorMapper {

    public PColorDTO toDto(PColor entity){
        return new PColorDTO(
                entity.getName(),
                entity.getLastModifiedAt()
        );
    }
    public PColor toEntity(PColorDTO dto){
        return new PColor(
                dto.name()
        );
    }
}
