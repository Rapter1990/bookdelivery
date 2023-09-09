package com.example.demo.payload.request;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedFindAllRequest {

    @Valid
    private DateIntervalRequest dateIntervalRequest;

    @Valid
    private PaginationRequest paginationRequest;

}
