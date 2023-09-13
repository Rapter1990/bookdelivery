package com.example.demo.service.impl;

import com.example.demo.exception.book.BookNotFoundException;
import com.example.demo.model.Book;
import com.example.demo.model.mapper.book.BookMapper;
import com.example.demo.payload.request.PaginatedFindAllRequest;
import com.example.demo.payload.request.book.BookCreateRequest;
import com.example.demo.payload.request.book.BookUpdateRequest;
import com.example.demo.payload.request.book.BookUpdateStockRequest;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Book createBook(BookCreateRequest request) {

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
    public Book getBookById(final String bookId) {

        return bookRepository.findById(bookId)
                .orElseThrow(
                        () -> new BookNotFoundException(bookId));
    }

    @Override
    public Book updateBookStockById(String bookId, BookUpdateStockRequest request) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        book.setStock(request.getStock());

        return bookRepository.save(book);
    }

    /**
     * Returns all {@link Book} entities.
     * @return Book entities in a list.
     */
    @Override
    public Page<Book> getAllBooks(PaginatedFindAllRequest paginatedFindAllRequest) {
        Page<Book> page = bookRepository
                .findAll(PageRequest.of(paginatedFindAllRequest.getPaginationRequest().getPage(),
                         paginatedFindAllRequest.getPaginationRequest().getSize()));

        if (page.stream().noneMatch(book -> true)) {
            throw new BookNotFoundException("No books found");
        }

        return page;
    }

    /**
     * The method that updates the {@link Book} entity.
     *
     * @param bookId Represents the id of the {@link Book} entity to be updated.
     * @param request {@link BookUpdateRequest} represents the request body of the Book entity to be updated.
     * @return {@link Book} entity that is updated.
     */
    @Override
    public Book updateBookById(final String bookId, final BookUpdateRequest request) {
        final Book bookEntityToBeUpdate = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        BookMapper.mapForUpdating(bookEntityToBeUpdate, request);

        return bookRepository.save(bookEntityToBeUpdate);
    }
}
