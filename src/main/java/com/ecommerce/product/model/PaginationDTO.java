package com.ecommerce.product.model;

public record PaginationDTO(
        int page,
        int limit,
        String sortDirection,
        String sortBy
) {
}
