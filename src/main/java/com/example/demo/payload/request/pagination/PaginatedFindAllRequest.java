package com.example.demo.payload.request.pagination;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request object for paginated find-all queries.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginatedFindAllRequest {

    @Valid
    private DateIntervalRequest dateIntervalRequest;

    @Valid
    private PaginationRequest paginationRequest;

}
