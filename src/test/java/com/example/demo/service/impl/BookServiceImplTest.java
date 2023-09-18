package com.example.demo.service.impl;

import com.example.demo.base.BaseServiceTest;
import com.example.demo.builder.BookBuilder;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        Book mockBook = new BookBuilder().withValidFields().build();

        BookDTO mockBookDTO = BookMapper.toDTO(mockBook);

        // When
        when(bookRepository.save(any(Book.class))).thenReturn(mockBook);

        // Then
        BookDTO response = bookService.createBook(mockCreateRequest);

        assertEquals(mockBookDTO, response);

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void givenValidBookId_whenBookFound_returnBook() {

        // Given
        String mockBookId = RandomUtil.generateUUID();

        Book mockBook = new BookBuilder().withValidFields().withId(mockBookId).build();

        BookDTO mockBookDTO = BookMapper.toDTO(mockBook);

        // When
        when(bookRepository.findById(Mockito.anyString())).thenReturn(Optional.of(mockBook));

        // Then
        BookDTO response = bookService.getBookById(mockBookId);

        assertEquals(mockBookDTO, response);

        verify(bookRepository, times(1)).findById(Mockito.anyString());
    }

    @Test
    void givenValidBookId_whenBookNotFound_throwBookNotFoundException() {

        // Given
        String mockBookId = RandomUtil.generateUUID();

        // When
        when(bookRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        // Then
        assertThrows(
                BookNotFoundException.class,
                () -> bookService.getBookById(mockBookId)
        );

        verify(bookRepository, times(1)).findById(Mockito.anyString());
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
                .build();


        // When
        when(bookRepository.findById(Mockito.anyString())).thenReturn(Optional.of(mockBook));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> {
            Book updatedBook = invocation.getArgument(0);
            updatedBook.setStock(mockRequest.getStock());
            return updatedBook;
        });

        // Then
        BookDTO response = bookService.updateBookStockById(mockBookId, mockRequest);

        assertEquals(mockRequest.getStock(), response.getStock());
        verify(bookRepository, times(1)).findById(Mockito.anyString());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void givenValidBookIdAndBookUpdateStockRequest_whenBookNotFound_throwBookNotFoundException() {

        // Given
        String mockBookId = RandomUtil.generateUUID();

        BookUpdateStockRequest mockRequest = BookUpdateStockRequest.builder()
                .stock(123)
                .build();

        // When
        when(bookRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        // Then
        assertThrows(
                BookNotFoundException.class,
                () -> bookService.updateBookStockById(mockBookId, mockRequest)
        );

        verify(bookRepository, times(1)).findById(Mockito.anyString());
        verify(bookRepository, Mockito.never()).save(any(Book.class));
    }


    @Test
    void givenPaginatedFindAllRequest_WhenNoBooksFound_throwBookNotFoundException() {

        // Given
        PaginationRequest request = new PaginationRequest(1, 10);

        Page<Book> emptyPage = new PageImpl<>(Collections.emptyList());

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        // When and Then
        try {
            bookService.getAllBooks(request);
        } catch (BookNotFoundException e) {
            // Expected exception
            assertEquals("No book found with ID: No books found", e.getMessage());
        }

        verify(bookRepository, times(1)).findAll(any(Pageable.class));

    }

    @Test
    void givenPaginatedFindAllRequest_WhenBooksFound_ReturnPageBookList() {

        // given
        PaginationRequest request = new PaginationRequest(1, 10);

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
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(pageWithBooks);

        // then
        Page<BookDTO> result = bookService.getAllBooks(request);

        assertEquals(books.size(), result.getContent().size());

        verify(bookRepository, times(1)).findAll(any(Pageable.class));

    }

    @Test
    void givenBookUpdateRequest_WhenBookFound_ReturnUpdatedBook() {
        // Given
        String bookId = "123";
        BookUpdateRequest updateRequest = BookUpdateRequest.builder()
                .isbn("ISBN1234567")
                .name("Updated Author")
                .authorFullName("Updated Author")
                .stock(10)
                .price(BigDecimal.valueOf(19.99))
                .build();

        Book existingBook = Book.builder()
                .id(bookId)
                .isbn("ISBN1234567890")
                .name("Test Book")
                .authorFullName("Test Author")
                .stock(5)
                .price(BigDecimal.valueOf(9.99))
                .build();

        Book updatedBook = Book.builder()
                .id(bookId)
                .isbn(updateRequest.getIsbn())
                .name(updateRequest.getName())
                .authorFullName(updateRequest.getAuthorFullName())
                .stock(updateRequest.getStock())
                .price(updateRequest.getPrice())
                .build();

        // When
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        // Then
        BookDTO book = bookService.updateBookById(bookId, updateRequest);

        assertEquals(updateRequest.getIsbn(), book.getIsbn());
        assertEquals(updateRequest.getName(), book.getName());
        assertEquals(updateRequest.getAuthorFullName(), book.getAuthorFullName());
        assertEquals(updateRequest.getStock(), book.getStock());
        assertEquals(updateRequest.getPrice(), book.getPrice());

        // verify
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(existingBook);

    }

    @Test
    void givenBookUpdateRequest_WhenBookNotFound_ThrowBookNotFoundException() {
        // Given
        String bookId = "123";
        BookUpdateRequest updateRequest = BookUpdateRequest.builder()
                .isbn("ISBN1234567")
                .name("Updated Author")
                .authorFullName("Updated Author")
                .stock(10)
                .price(BigDecimal.valueOf(19.99))
                .build();

        // when
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // then
        assertThrows(BookNotFoundException.class, () -> bookService.updateBookById(bookId, updateRequest));

        // verify
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, never()).save(any());

    }

    @Test
    void givenBookDtoAndAmount_whenBookDtoStockGreaterThanAmount_thenReturnTrue() {

        // Given
        int amount = 50;
        BookDTO mockBookDTO = BookDTO.builder().stock(amount + 1).build();

        // Then
        boolean response = bookService.isStockAvailable(mockBookDTO, amount);

        Assertions.assertTrue(response);
    }

    @Test
    void givenBookDtoAndAmount_whenBookDtoStockLessThanAmount_thenThrowNoAvailableStockException() {

        // Given
        int amount = 100;
        BookDTO mockBookDTO = BookDTO.builder().stock(amount - 1).build();

        // Then
        Assertions.assertThrows(
                NoAvailableStockException.class,
                () -> bookService.isStockAvailable(mockBookDTO, amount)
        );
    }

}