package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.payload.request.BookCreateRequest;
import com.example.demo.payload.request.BookUpdateStockRequest;

public interface BookService {

    Book createBook(BookCreateRequest request);

    Book getBookById(String bookId);

    Book updateBookStockById(String bookId, BookUpdateStockRequest request);
    // check stock of Book


}
