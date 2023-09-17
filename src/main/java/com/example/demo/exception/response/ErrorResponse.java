package com.example.demo.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an error response containing information about a failed operation or request.
 * This class provides details such as the error message, HTTP status, status code, error details,
 * and a timestamp indicating when the error occurred.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    /**
     * The human-readable error message describing the reason for the failure.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    /**
     * The HTTP status indicating the type of error (e.g., 404 Not Found).
     */
    private HttpStatus status;

    /**
     * The numerical representation of the HTTP status code.
     */
    private Integer statusCode;

    /**
     * Additional details or error messages.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errorDetails;

    /**
     * The timestamp indicating when the error occurred.
     */
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}