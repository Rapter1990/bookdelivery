package com.example.demo.model.mapper.book;


import com.example.demo.dto.BookDTO;
import com.example.demo.model.Book;
import com.example.demo.payload.request.book.BookCreateRequest;
import com.example.demo.payload.request.book.BookUpdateRequest;
import com.example.demo.payload.response.CustomPageResponse;
import com.example.demo.payload.response.book.BookCreatedResponse;
import com.example.demo.payload.response.book.BookGetResponse;
import com.example.demo.payload.response.book.BookUpdatedResponse;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

/**
 * Utility class for mapping between {@link Book} entities and DTOs.
 */
@UtilityClass
public class BookMapper {

    /**
     * Maps a {@link BookCreateRequest} to a {@link Book} entity for saving.
     *
     * @param request The {@link BookCreateRequest} to be mapped.
     * @return A new {@link Book} entity populated with data from the request.
     */
    public static Book mapForSaving(BookCreateRequest request) {
        return Book.builder()
                .isbn(request.getIsbn())
                .name(request.getName())
                .authorFullName(request.getAuthorFullName())
                .stock(request.getStock())
                .price(request.getPrice())
                .build();
    }

    /**
     * Updates the {@link Book} entity given as a parameter using the
     * {@link BookUpdateRequest} DTO object given as a parameter. <br>
     * This method has no return, the update operation is performed through
     * the reference of the object.
     *
     * @param bookEntityToBeUpdate {@link Book} entity to be updated
     * @param request              {@link BookUpdateRequest} request DTO object containing update details
     */
    public static void mapForUpdating(Book bookEntityToBeUpdate, BookUpdateRequest request) {
        bookEntityToBeUpdate.setIsbn(request.getIsbn());
        bookEntityToBeUpdate.setName(request.getName());
        bookEntityToBeUpdate.setAuthorFullName(request.getAuthorFullName());
        bookEntityToBeUpdate.setStock(request.getStock());
        bookEntityToBeUpdate.setPrice(request.getPrice());
    }

    /**
     * Converts a {@link BookDTO} to a {@link BookCreatedResponse}.
     *
     * @param source The source {@link BookDTO} to be converted.
     * @return A {@link BookCreatedResponse} containing data from the source DTO.
     */
    public static BookCreatedResponse toCreatedResponse(BookDTO source) {
        return BookCreatedResponse.builder()
                .id(source.getId())
                .isbn(source.getIsbn())
                .name(source.getName())
                .authorFullName(source.getAuthorFullName())
                .stock(source.getStock())
                .price(source.getPrice())
                .build();
    }

    /**
     * Converts a {@link BookDTO} to a {@link BookGetResponse}.
     *
     * @param source The source {@link BookDTO} to be converted.
     * @return A {@link BookGetResponse} containing data from the source DTO.
     */
    public static BookGetResponse toGetResponse(BookDTO source) {
        if (source == null) {
            return null;
        }

        return BookGetResponse.builder()
                .id(source.getId())
                .isbn(source.getIsbn())
                .name(source.getName())
                .authorFullName(source.getAuthorFullName())
                .stock(source.getStock())
                .price(source.getPrice())
                .build();

    }

    /**
     * Converts a {@link Page<BookDTO>} to a {@link CustomPageResponse<BookGetResponse>}.
     *
     * @param sources The source {@link Page<BookDTO>} to be converted.
     * @return A {@link CustomPageResponse<BookGetResponse>} containing converted data.
     */
    public static CustomPageResponse<BookGetResponse> toGetResponse(Page<BookDTO> sources) {

        return CustomPageResponse.of(sources.map(BookMapper::toGetResponse));
    }

    /**
     * Converts a {@link BookDTO} to a {@link BookUpdatedResponse}.
     *
     * @param source The source {@link BookDTO} to be converted.
     * @return A {@link BookUpdatedResponse} containing data from the source DTO.
     */
    public static BookUpdatedResponse toUpdatedResponse(BookDTO source) {
        if (source == null) {
            return null;
        }

        return BookUpdatedResponse.builder()
                .id(source.getId())
                .isbn(source.getIsbn())
                .name(source.getName())
                .authorFullName(source.getAuthorFullName())
                .stock(source.getStock())
                .price(source.getPrice())
                .build();
    }

    /**
     * Converts a {@link Book} entity to a {@link BookDTO}.
     *
     * @param book The {@link Book} entity to be converted.
     * @return A {@link BookDTO} containing data from the source entity.
     */
    public static BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .name(book.getName())
                .authorFullName(book.getAuthorFullName())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .stock(book.getStock())
                .build();
    }

    /**
     * Converts a {@link BookDTO} to a {@link Book} entity.
     *
     * @param bookDTO The {@link BookDTO} to be converted.
     * @return A {@link Book} entity containing data from the source DTO.
     */
    public static Book toBook(BookDTO bookDTO) {
        return Book.builder()
                .id(bookDTO.getId())
                .name(bookDTO.getName())
                .authorFullName(bookDTO.getAuthorFullName())
                .isbn(bookDTO.getIsbn())
                .price(bookDTO.getPrice())
                .stock(bookDTO.getStock())
                .build();

    }

}
