package com.ecommerce.enquiry;

import org.springframework.stereotype.Service;

@Service
public class EnquiryMapper {

    public EnquiryDTO toDto(Enquiry entity){
        return new EnquiryDTO(
                entity.getName(),
                entity.getEmail(),
                entity.getMobile(),
                entity.getComment(),
                entity.getLastModifiedAt()
        );
    }
    public Enquiry toEntity(EnquiryDTO dto){
        return new Enquiry(
                dto.name(),
                dto.email(),
                dto.mobile(),
                dto.comment()
        );
    }
}
