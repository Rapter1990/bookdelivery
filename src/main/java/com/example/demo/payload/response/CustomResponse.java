package com.example.demo.payload.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class CustomResponse<T> {

    public CustomResponse(T response, HttpStatus status) {
        this.response = response;
        this.httpStatus = status;
        this.isSuccess = status.is2xxSuccessful();
    }

    private T response;

    private Boolean isSuccess;

    private HttpStatus httpStatus;

    @Builder.Default
    private Long time = System.currentTimeMillis();

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
    public static <E> CustomResponse<E> ok(E response){
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
    public static <E> CustomResponse<E> created(E response){
        return CustomResponse.<E>builder()
                .response(response)
                .isSuccess(true)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }
}
