package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.mapper.book.BookMapper;
import com.example.demo.payload.request.BookCreateRequest;
import com.example.demo.payload.request.BookUpdateStockRequest;
import com.example.demo.payload.response.book.BookCreatedResponse;
import com.example.demo.payload.response.book.BookGetResponse;
import com.example.demo.payload.response.book.BookUpdatedResponse;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * Creates a new {@link Book}.
     *
     * @param request
     * @return
     */
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookCreatedResponse> createBook(
            @RequestBody @Valid final BookCreateRequest request
    ) {
        final Book createdBookEntity = bookService.createBook(request);
        final BookCreatedResponse response = BookMapper.toCreatedResponse(createdBookEntity);

        return ResponseEntity.ok(response);
    }

    /**
     * Updates a {@link Book}'s stock
     *
     * @param bookId  The specified book id
     * @param request {@link BookUpdateStockRequest}
     * @return Response entity of {@link BookUpdatedResponse}
     */
    @PutMapping("/{bookId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookUpdatedResponse> updateStock(
            @PathVariable String bookId,
            @RequestBody @Valid final BookUpdateStockRequest request
    ) {
        final Book updatedBookEntity = bookService.updateBookStockById(bookId, request);
        final BookUpdatedResponse response = BookMapper.toUpdatedResponse(updatedBookEntity);

        return ResponseEntity.ok(response);
    }

    /**
     * Returns {@link Book} by using specified bookId
     *
     * @param bookId
     * @return
     */
    @GetMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
    public ResponseEntity<BookGetResponse> getBookById(
            @PathVariable("bookId") final String bookId
    ) {
        final Book bookEntityFromDb = bookService.getBookById(bookId);
        final BookGetResponse response = BookMapper.toGetResponse(bookEntityFromDb);

        return ResponseEntity.ok(response);
    }

}
