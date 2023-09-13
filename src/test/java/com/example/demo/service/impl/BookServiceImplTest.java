package com.example.demo.service.impl;

import com.example.demo.base.BaseServiceTest;
import com.example.demo.exception.book.BookNotFoundException;
import com.example.demo.model.Book;
import com.example.demo.payload.request.DateIntervalRequest;
import com.example.demo.payload.request.PaginatedFindAllRequest;
import com.example.demo.payload.request.PaginationRequest;
import com.example.demo.payload.request.book.BookCreateRequest;
import com.example.demo.payload.request.book.BookUpdateStockRequest;
import com.example.demo.repository.BookRepository;
import com.example.demo.util.RandomUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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

        assertEquals(mockBook, response);

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

        assertEquals(mockBook, response);

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
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenAnswer(invocation -> {
            Book updatedBook = invocation.getArgument(0);
            updatedBook.setStock(mockRequest.getStock());
            return updatedBook;
        });

        // Then
        Book response = bookService.updateBookStockById(mockBookId, mockRequest);

        assertEquals(mockRequest.getStock(), response.getStock());
        Mockito.verify(bookRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any(Book.class));
    }

    @Test
    void givenValidBookIdAndBookUpdateStockRequest_whenBookNotFound_throwBookNotFoundException() {

        // Given
        String mockBookId = RandomUtil.generateUUID();

        BookUpdateStockRequest mockRequest = BookUpdateStockRequest.builder()
                .stock(123)
                .build();

        // When
        Mockito.when(bookRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(
                BookNotFoundException.class,
                () -> bookService.updateBookStockById(mockBookId, mockRequest)
        );

        Mockito.verify(bookRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(bookRepository, Mockito.never()).save(Mockito.any(Book.class));
    }


    @Test
    void givenPaginatedFindAllRequest_WhenNoBooksFound_throwBookNotFoundException() {
        // Given

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 2000);
        calendar1.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar1.set(Calendar.DAY_OF_MONTH, 10);

        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 2000);
        calendar1.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar1.set(Calendar.DAY_OF_MONTH, 13);

        Date date1 = calendar1.getTime();
        Date date2 = calendar2.getTime();

        PaginatedFindAllRequest request = PaginatedFindAllRequest.builder()
                .dateIntervalRequest(new DateIntervalRequest(date1,date2))
                .paginationRequest(new PaginationRequest(1,10))
                .build();

        Page<Book> emptyPage = new PageImpl<>(Collections.emptyList());

        Mockito.when(bookRepository.findAll(Mockito.any(Pageable.class))).thenReturn(emptyPage);

        // When and Then
        try {
            bookService.getAllBooks(request);
            fail("BookNotFoundException should have been thrown.");
        } catch (BookNotFoundException e) {
            // Expected exception
            assertEquals("No book found with ID: No books found", e.getMessage());
        }

        Mockito.verify(bookRepository, Mockito.times(1)).findAll(Mockito.any(Pageable.class));

    }

    @Test
    void givenPaginatedFindAllRequest_WhenBooksFound_throwReturnPageBookList() {

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 2000);
        calendar1.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar1.set(Calendar.DAY_OF_MONTH, 10);

        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 2000);
        calendar1.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar1.set(Calendar.DAY_OF_MONTH, 13);

        Date date1 = calendar1.getTime();
        Date date2 = calendar2.getTime();

        PaginatedFindAllRequest request = PaginatedFindAllRequest.builder()
                .dateIntervalRequest(new DateIntervalRequest(date1,date2))
                .paginationRequest(new PaginationRequest(1,10))
                .build();

        List<Book> books = Arrays.asList(
                Book.builder()
                        .id("1")
                        .isbn("ISBN-12345")
                        .name("Book 1")
                        .authorFullName("Author 1")
                        .stock(10)
                        .price(BigDecimal.valueOf(29.99))
                        .build(),
                Book.builder()
                        .id("2")
                        .isbn("ISBN-67890")
                        .name("Book 2")
                        .authorFullName("Author 2")
                        .stock(5)
                        .price(BigDecimal.valueOf(19.99))
                        .build()
        );
        Page<Book> pageWithBooks = new PageImpl<>(books);

        // when
        Mockito.when(bookRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageWithBooks);

        // then
        Page<Book> result = bookService.getAllBooks(request);

        assertEquals(books.size(), result.getContent().size());

    }

}