package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.payload.request.book.BookCreateRequest;
import com.example.demo.payload.request.book.BookUpdateRequest;
import com.example.demo.payload.request.book.BookUpdateStockRequest;
import com.example.demo.payload.request.pagination.PaginatedFindAllRequest;
import org.springframework.data.domain.Page;

public interface BookService {

    Book createBook(BookCreateRequest request);

    Book getBookById(String bookId);

    Book updateBookStockById(String bookId, BookUpdateStockRequest request);

    Page<Book> getAllBooks(PaginatedFindAllRequest paginatedFindAllRequest);

    Book updateBookById(String bookId, BookUpdateRequest request);

}
