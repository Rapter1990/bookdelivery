package com.example.demo.payload.request.pagination;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Represents a request object for pagination settings.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationRequest implements Paging {

    @Min(0)
    private int page = 0;

    @Min(1)
    @Max(50)
    private int size = 10;

    /**
     * Converts the fields of this instance to {@link Pageable}
     *
     * @return Pageable
     */
    @Override
    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}
