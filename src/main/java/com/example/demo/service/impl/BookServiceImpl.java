package com.example.demo.service.impl;

import com.example.demo.dto.BookDTO;
import com.example.demo.exception.book.BookNotFoundException;
import com.example.demo.exception.book.NoAvailableStockException;
import com.example.demo.model.Book;
import com.example.demo.model.mapper.book.BookMapper;
import com.example.demo.payload.request.book.BookCreateRequest;
import com.example.demo.payload.request.book.BookUpdateRequest;
import com.example.demo.payload.request.book.BookUpdateStockRequest;
import com.example.demo.payload.request.pagination.PaginatedFindAllRequest;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public BookDTO createBook(BookCreateRequest request) {

        final Book bookEntityToBeSaved = BookMapper.mapForSaving(request);

        return BookMapper.toDTO(bookRepository.save(bookEntityToBeSaved));
    }

    /**
     * Returns {@link Book} by given bookId.
     *
     * @param bookId
     * @return
     */
    public BookDTO getBookById(final String bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(
                        () -> new BookNotFoundException(bookId)
                );

        return BookMapper.toDTO(book);
    }

    @Override
    public BookDTO updateBookStockById(String bookId, BookUpdateStockRequest request) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        book.setStock(request.getStock());

        return BookMapper.toDTO(bookRepository.save(book));
    }

    /**
     * Returns all {@link Book} entities.
     *
     * @return Book entities in a list.
     */
    @Override
    public Page<BookDTO> getAllBooks(PaginatedFindAllRequest paginatedFindAllRequest) {

        return bookRepository
                .findAll(paginatedFindAllRequest.getPaginationRequest().toPageable())
                .map(BookMapper::toDTO);
    }

    /**
     * The method that updates the {@link Book} entity.
     *
     * @param bookId  Represents the id of the {@link Book} entity to be updated.
     * @param request {@link BookUpdateRequest} represents the request body of the Book entity to be updated.
     * @return {@link Book} entity that is updated.
     */
    @Override
    public BookDTO updateBookById(final String bookId, final BookUpdateRequest request) {
        final Book bookEntityToBeUpdate = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        BookMapper.mapForUpdating(bookEntityToBeUpdate, request);

        return BookMapper.toDTO(bookRepository.save(bookEntityToBeUpdate));
    }

    @Override
    public boolean isStockAvailable(BookDTO bookDTO, int amount) {
        if (bookDTO.getStock() < amount) {
            throw new NoAvailableStockException(amount);
        } else {
            return true;
        }
    }
}
