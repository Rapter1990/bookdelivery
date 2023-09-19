package com.example.demo.controller;

import com.example.demo.dto.BookDTO;
import com.example.demo.model.Book;
import com.example.demo.model.mapper.book.BookMapper;
import com.example.demo.payload.request.book.BookCreateRequest;
import com.example.demo.payload.request.book.BookUpdateRequest;
import com.example.demo.payload.request.book.BookUpdateStockRequest;
import com.example.demo.payload.request.pagination.PaginationRequest;
import com.example.demo.payload.response.CustomPageResponse;
import com.example.demo.payload.response.CustomResponse;
import com.example.demo.payload.response.book.BookCreatedResponse;
import com.example.demo.payload.response.book.BookGetResponse;
import com.example.demo.payload.response.book.BookUpdatedResponse;
import com.example.demo.service.BookService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class BookController {

    private final BookService bookService;

    /**
     * Creates a new Book entity.
     *
     * @param request The request body containing information for creating the Book.
     * @return Response containing information about the created Book.
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse<BookCreatedResponse> createBook(
            @RequestBody @Valid final BookCreateRequest request
    ) {
        final BookDTO createdBookEntity = bookService.createBook(request);
        final BookCreatedResponse response = BookMapper.toCreatedResponse(createdBookEntity);

        return CustomResponse.created(response);
    }

    /**
     * Updates a {@link Book}'s stock
     *
     * @param bookId  The specified book id
     * @param request {@link BookUpdateStockRequest}
     * @return Response entity of {@link BookUpdatedResponse}
     */
    @PutMapping("/stock-amount/{bookId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CustomResponse<BookUpdatedResponse> updateStock(@PathVariable String bookId, @RequestBody @Valid final BookUpdateStockRequest request) {
        final BookDTO updatedBookEntity = bookService.updateBookStockById(bookId, request);
        final BookUpdatedResponse response = BookMapper.toUpdatedResponse(updatedBookEntity);

        return CustomResponse.ok(response);
    }

    /**
     * The endpoint that updates the {@link Book} entity.
     *
     * @param bookId  Represents the id of the {@link Book} entity to be updated.
     * @param request Represents the request body of the Book entity to be updated.
     * @return BookUpdatedResponse -> Represents the response body of the Book entity to be updated.
     */
    @PutMapping("/{bookId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CustomResponse<BookUpdatedResponse> updateBookByBookId(@PathVariable("bookId") final String bookId,
                                                                  @RequestBody @Valid final BookUpdateRequest request) {
        final BookDTO updatedBookEntity = bookService
                .updateBookById(bookId, request);
        final BookUpdatedResponse response = BookMapper
                .toUpdatedResponse(updatedBookEntity);

        return CustomResponse.ok(response);
    }

    /**
     * Retrieves a Book by its ID.
     *
     * @param bookId The ID of the Book to retrieve.
     * @return Response containing information about the requested Book.
     */
    @GetMapping("/{bookId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CUSTOMER')")
    public CustomResponse<BookGetResponse> getBookById(@PathVariable("bookId") final String bookId) {
        final BookDTO bookEntityFromDb = bookService.getBookById(bookId);
        final BookGetResponse response = BookMapper.toGetResponse(bookEntityFromDb);

        return CustomResponse.ok(response);
    }

    /**
     * Returns all {@link Book} entities.
     *
     * @return list of Book entities
     */
    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CUSTOMER')")
    public CustomResponse<CustomPageResponse<BookGetResponse>> getBooks(@RequestBody @Valid PaginationRequest paginationRequest) {
        final Page<BookDTO> bookEntitiesFromDb = bookService.getAllBooks(paginationRequest);
        final CustomPageResponse<BookGetResponse> responses = BookMapper
                .toGetResponse(bookEntitiesFromDb);

        return CustomResponse.ok(responses);
    }

}
