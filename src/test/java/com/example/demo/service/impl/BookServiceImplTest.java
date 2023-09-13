package com.example.demo.service.impl;

import com.example.demo.base.BaseServiceTest;
import com.example.demo.model.Book;
import com.example.demo.payload.request.book.BookCreateRequest;
import com.example.demo.repository.BookRepository;
import com.example.demo.util.RandomUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;

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

        Assertions.assertEquals(response.getId(), mockBook.getId());
        Assertions.assertEquals(response.getName(), mockBook.getName());
        Assertions.assertEquals(response.getIsbn(), mockBook.getIsbn());
        Assertions.assertEquals(response.getPrice(), mockBook.getPrice());
        Assertions.assertEquals(response.getAuthorFullName(), mockBook.getAuthorFullName());
        Assertions.assertEquals(response.getVersion(), mockBook.getVersion());

        Mockito.verify(bookRepository,Mockito.times(1)).save(Mockito.any(Book.class));
    }
}