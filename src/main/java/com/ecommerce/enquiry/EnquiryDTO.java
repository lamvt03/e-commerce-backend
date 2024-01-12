package com.ecommerce.enquiry;

import java.time.LocalDateTime;

public record EnquiryDTO(
         Long id,
         String name,
         String email,
         String mobile,
         String comment,
         LocalDateTime createdAt
) {
}
