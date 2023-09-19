package com.example.demo.service.impl;

import com.example.demo.dto.BookDTO;
import com.example.demo.exception.book.BookNotFoundException;
import com.example.demo.exception.book.NoAvailableStockException;
import com.example.demo.model.Book;
import com.example.demo.model.mapper.book.BookMapper;
import com.example.demo.payload.request.book.BookCreateRequest;
import com.example.demo.payload.request.book.BookUpdateRequest;
import com.example.demo.payload.request.book.BookUpdateStockRequest;
import com.example.demo.payload.request.pagination.PaginationRequest;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link BookService} interface for creating and managing books.
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    /**
     * Creates a new book based on the provided request.
     *
     * @param request The request containing book information.
     * @return A {@link BookDTO} representing the newly created book.
     */
    public BookDTO createBook(BookCreateRequest request) {

        final Book bookEntityToBeSaved = BookMapper.mapForSaving(request);

        return BookMapper.toDTO(bookRepository.save(bookEntityToBeSaved));
    }

    /**
     * Retrieves a book by its unique identifier.
     *
     * @param bookId The unique identifier of the book.
     * @return A {@link BookDTO} representing the requested book.
     */
    @Transactional
    public BookDTO getBookById(final String bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(
                        () -> new BookNotFoundException(bookId)
                );

        return BookMapper.toDTO(book);
    }

    /**
     * Updates the stock quantity of a book by its unique identifier.
     *
     * @param bookId The unique identifier of the book.
     * @param request The request containing the updated stock information.
     * @return A {@link BookDTO} representing the book after the stock update.
     */
    @Override
    @Transactional
    public BookDTO updateBookStockById(String bookId, BookUpdateStockRequest request) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        book.setStock(request.getStock());

        return BookMapper.toDTO(bookRepository.save(book));
    }

    /**
     * Retrieves a paginated list of all books based on the provided request.
     *
     * @param paginationRequest The request containing pagination information.
     * @return A {@link Page} of {@link BookDTO} objects representing the list of books.
     */
    @Override
    public Page<BookDTO> getAllBooks(PaginationRequest paginationRequest) {

        return bookRepository
                .findAll(paginationRequest.toPageable())
                .map(BookMapper::toDTO);
    }

    /**
     * Updates a book by its unique identifier.
     *
     * @param bookId The unique identifier of the book.
     * @param request The request containing the updated book information.
     * @return A {@link BookDTO} representing the book after the update.
     */
    @Override
    @Transactional
    public BookDTO updateBookById(final String bookId, final BookUpdateRequest request) {
        final Book bookEntityToBeUpdate = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        BookMapper.mapForUpdating(bookEntityToBeUpdate, request);

        return BookMapper.toDTO(bookRepository.save(bookEntityToBeUpdate));
    }

    /**
     * Checks if a given amount of a book is available in stock.
     *
     * @param bookDTO The {@link BookDTO} representing the book to check.
     * @param amount The amount of the book to check for availability.
     * @return {@code true} if the specified amount is available in stock, {@code false} otherwise.
     */
    @Override
    public boolean isStockAvailable(BookDTO bookDTO, int amount) {
        if (bookDTO.getStock() < amount) {
            throw new NoAvailableStockException(amount);
        } else {
            return true;
        }

    }

}
