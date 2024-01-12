package com.ecommerce.enquiry.request;

public record EnquiryCreateRequest(
        String name,
        String email,
        String mobile,
        String comment
) {
}
