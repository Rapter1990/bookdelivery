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

@UtilityClass
public class BookMapper {

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


    public static CustomPageResponse<BookGetResponse> toGetResponse(Page<BookDTO> sources) {

        return CustomPageResponse.of(sources.map(BookMapper::toGetResponse));
    }

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

    public static BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .name(book.getName())
                .authorFullName(book.getAuthorFullName())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .build();
    }
}
