package com.ecommerce.enquiry;

import com.ecommerce.enquiry.request.EnquiryCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class EnquiryMapper {

    public EnquiryDTO toDto(Enquiry entity){
        return new EnquiryDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getMobile(),
                entity.getComment(),
                entity.getCreatedAt()
        );
    }
    public Enquiry toEntity(EnquiryCreateRequest request){
        return new Enquiry(
                request.name(),
                request.email(),
                request.mobile(),
                request.comment()
        );
    }
}
