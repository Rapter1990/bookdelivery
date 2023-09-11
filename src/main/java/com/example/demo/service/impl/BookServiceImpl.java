package com.example.demo.service.impl;

import com.example.demo.model.Book;
import com.example.demo.model.mapper.book.BookMapper;
import com.example.demo.payload.request.BookCreateRequest;
import com.example.demo.payload.request.BookUpdateStockRequest;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    /**
     * Creates a {@link Book} entitiy from {@link BookCreateRequest}
     * and return.
     *
     * @param request
     * @return
     */
    public Book createBook(
            BookCreateRequest request
    ) {

        final Book bookEntityToBeSave = BookMapper.mapForSaving(request);

        bookEntityToBeSave.setVersion(0L);

        return bookRepository.save(bookEntityToBeSave);
    }

    /**
     * Returns {@link Book} by given bookId.
     *
     * @param bookId
     * @return
     */
    public Book getBookById(
            final String bookId
    ) {

        return bookRepository.findById(bookId)
                .orElseThrow(
                        () -> new RuntimeException("Book can not be found with given id: " + bookId));
    }

    @Override
    public Book updateBookStockById(
            String bookId,
            BookUpdateStockRequest request
    ) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("BOOK DOES NOT EXIST BY ID"));
        book.setStock(request.getStock());

        return bookRepository.save(book);
    }
}
