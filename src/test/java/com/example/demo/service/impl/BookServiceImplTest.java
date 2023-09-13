package com.example.demo.service.impl;

import com.example.demo.base.BaseServiceTest;
import com.example.demo.exception.book.BookNotFoundException;
import com.example.demo.model.Book;
import com.example.demo.payload.request.book.BookCreateRequest;
import com.example.demo.payload.request.book.BookUpdateStockRequest;
import com.example.demo.repository.BookRepository;
import com.example.demo.util.RandomUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

class BookServiceImplTest extends BaseServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    void givenValidBookCreateRequest_whenBookCreated_returnBook() {

        // Given
        BookCreateRequest mockCreateRequest = BookCreateRequest.builder()
                .name("Name")
                .authorFullName("Author Full Name")
                .isbn("1234567890")
                .stock(123)
                .price(BigDecimal.TEN)
                .build();

        Book mockBook = Book.builder()
                .id(RandomUtil.generateUUID())
                .name(mockCreateRequest.getName())
                .authorFullName(mockCreateRequest.getAuthorFullName())
                .isbn(mockCreateRequest.getIsbn())
                .stock(mockCreateRequest.getStock())
                .price(mockCreateRequest.getPrice())
                .version(0L)
                .build();

        // When
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(mockBook);

        // Then
        Book response = bookService.createBook(mockCreateRequest);

        Assertions.assertEquals(mockBook, response);

        Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any(Book.class));
    }

    @Test
    void givenValidBookId_whenBookFound_returnBook() {

        // Given
        String mockBookId = RandomUtil.generateUUID();

        Book mockBook = Book.builder()
                .id(mockBookId)
                .name("Name")
                .authorFullName("Author Full Name")
                .isbn("1234567890")
                .stock(123)
                .price(BigDecimal.TEN)
                .version(0L)
                .build();

        // When
        Mockito.when(bookRepository.findById(Mockito.anyString())).thenReturn(Optional.of(mockBook));

        // Then
        Book response = bookService.getBookById(mockBookId);

        Assertions.assertEquals(mockBook, response);

        Mockito.verify(bookRepository, Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    void givenValidBookId_whenBookNotFound_throwBookNotFoundException() {

        // Given
        String mockBookId = RandomUtil.generateUUID();

        // When
        Mockito.when(bookRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(
                BookNotFoundException.class,
                () -> bookService.getBookById(mockBookId)
        );

        Mockito.verify(bookRepository, Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    void givenValidBookIdAndBookUpdateStockRequest_whenBookUpdated_thenReturnBook() {

        // Given
        String mockBookId = RandomUtil.generateUUID();

        BookUpdateStockRequest mockRequest = BookUpdateStockRequest.builder()
                .stock(123)
                .build();

        Book mockBook = Book.builder()
                .id(mockBookId)
                .name("Name")
                .authorFullName("Author Full Name")
                .isbn("1234567890")
                .stock(321)
                .price(BigDecimal.TEN)
                .version(0L)
                .build();

        // When
        Mockito.when(bookRepository.findById(Mockito.anyString())).thenReturn(Optional.of(mockBook));

        // Then
        bookService.updateBookStockById(mockBookId, mockRequest);

        Assertions.assertEquals(mockRequest.getStock(), mockBook.getStock());
        Mockito.verify(bookRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any(Book.class));
    }
}