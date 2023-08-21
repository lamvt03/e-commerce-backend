package com.ecommerce.util;

import com.ecommerce.util.PaginationDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PaginationService {

    public Pageable getPageable(PaginationDTO paginationDTO){
        Sort sort = Sort.by(
                Sort.Direction.fromString(paginationDTO.sortDirection()),
                paginationDTO.sortBy()
        );
        return PageRequest.of(paginationDTO.page()-1, paginationDTO.limit(), sort);
    }
}
