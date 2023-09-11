package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.payload.request.BookCreateRequest;

public interface BookService {

    Book createBook(BookCreateRequest request);

    Book getBookById(String bookId);

    // update stock of Book

    // check stock of Book


}
