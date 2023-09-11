package com.example.demo.model.mapper.book;


import com.example.demo.model.Book;
import com.example.demo.payload.request.BookCreateRequest;
import com.example.demo.payload.response.book.BookCreatedResponse;
import com.example.demo.payload.response.book.BookGetResponse;

public class BookMapper {


    public static Book mapForSaving(
            BookCreateRequest request
    ) {
        return Book.builder()
                .isbn(request.getIsbn())
                .name(request.getName())
                .authorFullName(request.getAuthorFullName())
                .stock(request.getStock())
                .price(request.getPrice())
                .build();
    }

    public static BookCreatedResponse toCreatedResponse(
            Book book
    ) {
        return BookCreatedResponse.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .name(book.getName())
                .authorFullName(book.getAuthorFullName())
                .stock(book.getStock())
                .price(book.getPrice())
                .build();
    }


    public static BookGetResponse toGetResponse(
            Book book
    ) {
        if (book == null){
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




}
