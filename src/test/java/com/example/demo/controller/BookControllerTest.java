package com.example.demo.controller;

import com.example.demo.base.BaseControllerTest;
import com.example.demo.builder.BookBuilder;
import com.example.demo.dto.BookDTO;
import com.example.demo.model.Book;
import com.example.demo.model.mapper.book.BookMapper;
import com.example.demo.payload.request.book.BookCreateRequest;
import com.example.demo.payload.request.book.BookUpdateRequest;
import com.example.demo.payload.request.book.BookUpdateStockRequest;
import com.example.demo.payload.request.pagination.DateIntervalRequest;
import com.example.demo.payload.request.pagination.PaginatedFindAllRequest;
import com.example.demo.payload.request.pagination.PaginationRequest;
import com.example.demo.payload.response.CustomResponse;
import com.example.demo.payload.response.book.BookCreatedResponse;
import com.example.demo.payload.response.book.BookGetResponse;
import com.example.demo.service.impl.BookServiceImpl;
import com.example.demo.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends BaseControllerTest {

    @MockBean
    private BookServiceImpl bookService;

    @Test
    void givenCreateBookRequest_whenAdminRole_ReturnBookCreatedResponse() throws Exception {

        // Given
        BookCreateRequest mockRequest = BookCreateRequest.builder()
                .authorFullName("Author full name")
                .name("name")
                .isbn("1234567890")
                .stock(123)
                .price(BigDecimal.TEN)
                .build();

        Book book = new BookBuilder().withValidFields().build();

        BookDTO bookDTO = BookMapper.toDTO(book);

        // When
        BookCreatedResponse bookCreatedResponse = BookMapper.toCreatedResponse(bookDTO);
        CustomResponse<BookCreatedResponse> customResponseOfBookCreatedResponse = CustomResponse.ok(bookCreatedResponse);

        Mockito.when(bookService.createBook(mockRequest)).thenReturn(bookDTO);

        // Then
        mockMvc.perform(post("/api/v1/books")
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void givenBookId_whenAdminRoleAndBookFound_ReturnBookGetResponse() throws Exception {

        // given
        String bookId = RandomUtil.generateUUID();

        Book book = new BookBuilder().withValidFields().build();

        BookDTO bookDTO = BookMapper.toDTO(book);
        BookGetResponse response = BookMapper.toGetResponse(bookDTO);

        // when
        Mockito.when(bookService.getBookById(bookId)).thenReturn(bookDTO);

        // then
        mockMvc.perform(get("/api/v1/books/{orderId}", bookId)
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk());

    }

    @Test
    void givenBookId_whenCustomerRoleAndBookFound_ReturnBookGetResponse() throws Exception {

        // given
        String bookId = RandomUtil.generateUUID();

        Book book = new BookBuilder().withValidFields().build();

        BookDTO bookDTO = BookMapper.toDTO(book);
        BookGetResponse response = BookMapper.toGetResponse(bookDTO);

        // when
        Mockito.when(bookService.getBookById(bookId)).thenReturn(bookDTO);

        // then
        mockMvc.perform(get("/api/v1/books/{orderId}", bookId)
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk());

    }

    @Test
    void givenPaginatedFindAllRequest_whenCustomerRoleAndBookListFound_ReturnPageResponseBookGetResponse() throws Exception {

        // given
        String bookId1 = RandomUtil.generateUUID();
        String bookId2 = RandomUtil.generateUUID();

        Book book1 = new BookBuilder().withValidFields().withId(bookId1).build();

        Book book2 = new BookBuilder().withValidFields().withId(bookId2).build();

        BookDTO bookDTO1 = BookMapper.toDTO(book1);
        BookDTO bookDTO2 = BookMapper.toDTO(book2);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 2000);
        calendar1.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar1.set(Calendar.DAY_OF_MONTH, 10);

        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 2000);
        calendar1.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar1.set(Calendar.DAY_OF_MONTH, 13);

        LocalDateTime startDate = calendar1.getTime().toInstant().atZone(calendar1.getTimeZone().toZoneId()).toLocalDateTime();
        LocalDateTime endDate = calendar2.getTime().toInstant().atZone(calendar2.getTimeZone().toZoneId()).toLocalDateTime();

        PaginatedFindAllRequest request = PaginatedFindAllRequest.builder()
                .dateIntervalRequest(new DateIntervalRequest(startDate, endDate))
                .paginationRequest(new PaginationRequest(1, 10))
                .build();

        Page<BookDTO> mockPageOfOrderDTOs = new PageImpl<>(List.of(bookDTO1,bookDTO2));;

        // when
        Mockito.when(bookService.getAllBooks(any(PaginatedFindAllRequest.class))).thenReturn(mockPageOfOrderDTOs);

        // then
        mockMvc.perform(post("/api/v1/books/all")
                        .header(HttpHeaders.AUTHORIZATION, mockUserToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }

    @Test
    void givenPaginatedFindAllRequest_whenAdminRoleAndBookListFound_ReturnPageResponseBookGetResponse() throws Exception {

        // given
        String bookId1 = RandomUtil.generateUUID();
        String bookId2 = RandomUtil.generateUUID();

        Book book1 = new BookBuilder().withValidFields().withId(bookId1).build();

        Book book2 = new BookBuilder().withValidFields().withId(bookId2).build();

        BookDTO bookDTO1 = BookMapper.toDTO(book1);
        BookDTO bookDTO2 = BookMapper.toDTO(book2);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 2000);
        calendar1.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar1.set(Calendar.DAY_OF_MONTH, 10);

        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 2000);
        calendar1.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar1.set(Calendar.DAY_OF_MONTH, 13);

        LocalDateTime startDate = calendar1.getTime().toInstant().atZone(calendar1.getTimeZone().toZoneId()).toLocalDateTime();
        LocalDateTime endDate = calendar2.getTime().toInstant().atZone(calendar2.getTimeZone().toZoneId()).toLocalDateTime();

        PaginatedFindAllRequest request = PaginatedFindAllRequest.builder()
                .dateIntervalRequest(new DateIntervalRequest(startDate, endDate))
                .paginationRequest(new PaginationRequest(1, 10))
                .build();

        Page<BookDTO> mockPageOfOrderDTOs = new PageImpl<>(List.of(bookDTO1,bookDTO2));;

        // when
        Mockito.when(bookService.getAllBooks(any(PaginatedFindAllRequest.class))).thenReturn(mockPageOfOrderDTOs);

        // then
        mockMvc.perform(post("/api/v1/books/all")
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }

    @Test
    void givenBookIdAndBookUpdateStockRequest_whenAdminRoleAndBookFound_ReturnBookUpdatedResponse() throws Exception {

        // given
        Book book = new BookBuilder().withValidFields().build();
        String bookId = book.getId();

        BookUpdateStockRequest bookUpdateStockRequest = BookUpdateStockRequest.builder()
                .stock(5)
                .build();

        BookDTO updatedBookEntity = BookDTO.builder()
                .stock(bookUpdateStockRequest.getStock())
                .build();

        // when
        Mockito.when(bookService.updateBookStockById(bookId, bookUpdateStockRequest)).thenReturn(updatedBookEntity);


        // then
        mockMvc.perform(put("/api/v1/books/stock-amount/{bookId}", bookId)
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookUpdateStockRequest)))
                .andExpect(status().isOk());

    }

    @Test
    void givenBookIdAndBookUpdateRequest_whenAdminRoleAndBookFound_ReturnPageBookUpdatedResponse() throws Exception {

        // given
        Book book = new BookBuilder().withValidFields().build();
        String bookId = book.getId();

        BookUpdateRequest updateRequest = BookUpdateRequest.builder()
                .name("Update Book Name")
                .authorFullName("Update Book Author Full Name")
                .isbn("Update ISBN")
                .price(BigDecimal.valueOf(69.99))
                .build();

        BookDTO updatedBookEntity = BookDTO.builder()
                .id(bookId)
                .name(updateRequest.getName())
                .isbn(updateRequest.getIsbn())
                .authorFullName(updateRequest.getAuthorFullName())
                .price(updateRequest.getPrice())
                .build();

        // when
        Mockito.when(bookService.updateBookById(bookId, updateRequest)).thenReturn(updatedBookEntity);

        // then
        mockMvc.perform(put("/api/v1/books/{bookId}", bookId)
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

    }

}
