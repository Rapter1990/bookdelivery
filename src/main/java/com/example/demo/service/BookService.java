package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.payload.request.BookCreateRequest;
import com.example.demo.payload.request.BookUpdateStockRequest;

import java.util.List;

public interface BookService {

    Book createBook(BookCreateRequest request);

    Book getBookById(String bookId);

    Book updateBookStockById(String bookId, BookUpdateStockRequest request);

    List<Book> getBooks();
    // check stock of Book


}
