package com.example.demo.payload.request.pagination;

import org.springframework.data.domain.Pageable;

public interface Paging {

    Pageable toPageable();

}
