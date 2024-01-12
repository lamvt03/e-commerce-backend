package com.ecommerce.user.favorite;

import java.time.LocalDateTime;

public record FavoriteDTO(
        Boolean isLiked,
        LocalDateTime lastModifiedAt,
        Long productId
) {
}
