package com.example.demo.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

/**
 * Represents a custom response object with optional data and HTTP status.
 * @param <T> The type of the response data.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomResponse<T> {

    public CustomResponse(@Nullable T response, @Nonnull HttpStatus status) {
        this.response = response;
        this.httpStatus = status;
        this.isSuccess = status.is2xxSuccessful();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T response;

    private Boolean isSuccess;

    private HttpStatus httpStatus;

    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    public static final CustomResponse<Void> SUCCESS = CustomResponse.<Void>builder()
            .isSuccess(true)
            .httpStatus(HttpStatus.OK)
            .build();


    /**
     * Generates a successful response with an HTTP status of 200 OK.
     *
     * @param response The response data to be included in the CustomResponse.
     * @param <E>      The type of the response data.
     * @return A CustomResponse object containing the provided response data.
     */
    public static <E> CustomResponse<E> ok(E response) {
        return CustomResponse.<E>builder()
                .response(response)
                .isSuccess(true)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    /**
     * Generates a successful response with an HTTP status of 201 Created.
     *
     * @param response The response data to be included in the CustomResponse.
     * @param <E>      The type of the response data.
     * @return A CustomResponse object containing the provided response data.
     */
    @ResponseStatus(HttpStatus.CREATED)
    public static <E> CustomResponse<E> created(E response) {
        return CustomResponse.<E>builder()
                .response(response)
                .isSuccess(true)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }
}
