package com.ecommerce.enquiry;

import java.time.LocalDateTime;

public record EnquiryDTO(
         String name,
         String email,
         String mobile,
         String comment,
         LocalDateTime lastModifiedAt
) {
}
