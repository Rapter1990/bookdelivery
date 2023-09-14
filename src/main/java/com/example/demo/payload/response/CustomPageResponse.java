package com.example.demo.payload.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Represents a custom response containing paginated data.
 *
 * @param <T> The type of content elements in the response.
 */
@Getter
@Builder
public class CustomPageResponse<T> {

    /**
     * The list of content elements in the response.
     */
    private List<T> content;

    /**
     * The current page number.
     */
    private Integer pageNumber;

    /**
     * The number of elements per page.
     */
    private Integer pageSize;

    /**
     * The total number of pages.
     */
    private Integer totalPageCount;

    /**
     * The total number of elements across all pages.
     */
    private Long totalElementCount;

    /**
     * Creates a CustomPageResponse from a Spring Data Page object.
     *
     * @param page The Spring Data Page object to convert.
     * @param <T>  The type of content elements in the response.
     * @return A CustomPageResponse containing paginated data.
     */
    public static <T> CustomPageResponse<T> of(Page<T> page) {
        return CustomPageResponse.<T>builder()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalPageCount(page.getTotalPages())
                .totalElementCount(page.getTotalElements())
                .build();
    }

}
