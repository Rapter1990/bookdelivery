package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.payload.request.book.BookCreateRequest;
import com.example.demo.payload.request.book.BookUpdateRequest;
import com.example.demo.payload.request.book.BookUpdateStockRequest;
import com.example.demo.payload.request.pagination.PaginatedFindAllRequest;
import org.springframework.data.domain.Page;

public interface BookService {

    BookDTO createBook(BookCreateRequest request);

    BookDTO getBookById(String bookId);

    BookDTO updateBookStockById(String bookId, BookUpdateStockRequest request);

    Page<BookDTO> getAllBooks(PaginatedFindAllRequest paginatedFindAllRequest);

    BookDTO updateBookById(String bookId, BookUpdateRequest request);

    boolean isStockAvailable(BookDTO bookDTO, int amount);
}
