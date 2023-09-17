package com.example.demo.payload.request.pagination;

import org.springframework.data.domain.Pageable;

/**
 * Interface for converting objects to a Pageable for pagination.
 */
public interface Paging {

    /**
     * Converts the fields of implementing instances to a {@link Pageable}.
     *
     * @return Pageable
     */
    Pageable toPageable();

}
