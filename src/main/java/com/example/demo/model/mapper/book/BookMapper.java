package com.example.demo.model.mapper.book;


import com.example.demo.dto.BookDTO;
import com.example.demo.model.Book;
import com.example.demo.payload.request.book.BookCreateRequest;
import com.example.demo.payload.request.book.BookUpdateRequest;
import com.example.demo.payload.response.book.BookCreatedResponse;
import com.example.demo.payload.response.book.BookGetResponse;
import com.example.demo.payload.response.book.BookUpdatedResponse;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BookMapper {

    public static Book mapForSaving(BookCreateRequest request) {
        return Book.builder()
                .isbn(request.getIsbn())
                .name(request.getName())
                .authorFullName(request.getAuthorFullName())
                .stock(request.getStock())
                .price(request.getPrice())
                .build();
    }

    /**
     * Updates the {@link Book} entity given as a parameter using the
     * {@link BookUpdateRequest} DTO object given as a parameter. <br>
     * This method has no return, the update operation is performed through
     * the reference of the object.
     *
     * @param bookEntityToBeUpdate {@link Book} entity to be updated
     * @param request {@link BookUpdateRequest} request DTO object containing update details
     */
    public static void mapForUpdating(Book bookEntityToBeUpdate, BookUpdateRequest request) {
        bookEntityToBeUpdate.setIsbn(request.getIsbn());
        bookEntityToBeUpdate.setName(request.getName());
        bookEntityToBeUpdate.setAuthorFullName(request.getAuthorFullName());
        bookEntityToBeUpdate.setStock(request.getStock());
        bookEntityToBeUpdate.setPrice(request.getPrice());
    }

    public static BookCreatedResponse toCreatedResponse(Book book) {
        return BookCreatedResponse.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .name(book.getName())
                .authorFullName(book.getAuthorFullName())
                .stock(book.getStock())
                .price(book.getPrice())
                .build();
    }


    public static BookGetResponse toGetResponse(Book book) {
        if (book == null) {
            return null;
        }

        return BookGetResponse.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .name(book.getName())
                .authorFullName(book.getAuthorFullName())
                .stock(book.getStock())
                .price(book.getPrice())
                .build();

    }


    public static Page<BookGetResponse> toGetResponse(Page<Book> bookEntities) {
        List<BookGetResponse> bookGetResponses = bookEntities.getContent()
                .stream()
                .map(BookMapper::toGetResponse)
                .collect(Collectors.toList());

        Pageable pageable = bookEntities.getPageable();
        long totalElements = bookEntities.getTotalElements();

        return new PageImpl<>(bookGetResponses, pageable, totalElements);
    }

    public static BookUpdatedResponse toUpdatedResponse(Book book) {
        if (book == null) {
            return null;
        }

        return BookUpdatedResponse.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .name(book.getName())
                .authorFullName(book.getAuthorFullName())
                .stock(book.getStock())
                .price(book.getPrice())
                .build();
    }

    public static BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .name(book.getName())
                .authorFullName(book.getAuthorFullName())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .build();
    }
}
