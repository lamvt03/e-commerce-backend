package com.ecommerce.util.model;

public record PaginationDTO(
        int page,
        int limit,
        String sortDirection,
        String sortBy
) {
}
