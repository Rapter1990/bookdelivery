package com.example.demo.exception;

import com.example.demo.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Global exception handler for handling various HTTP request exceptions.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles the HTTP media type not supported exception, which triggers when the JSON content type is invalid.
     *
     * @param ex      The exception that was thrown.
     * @param headers The HTTP headers for the response.
     * @param status  The HTTP status code for the response.
     * @param request The web request.
     * @return A ResponseEntity containing an error response.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatusCode status,
                                                                     WebRequest request) {
        log.error(ex.getMessage(), ex);

        List<String> details = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        details.add(builder.toString());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Invalid JSON")
                .statusCode(status.value())
                .status(HttpStatus.valueOf(status.value()))
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }

    /**
     * Handles the HTTP message not readable exception, which triggers when the JSON request is malformed.
     *
     * @param ex      The exception that was thrown.
     * @param headers The HTTP headers for the response.
     * @param status  The HTTP status code for the response.
     * @param request The web request.
     * @return A ResponseEntity containing an error response.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        log.error(ex.getMessage(), ex);


        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Malformed JSON request")
                .statusCode(status.value())
                .status(HttpStatus.valueOf(status.value()))
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }

    /**
     * Handles the method argument not valid exception, which triggers when the @Valid annotation fails during input validation.
     *
     * @param ex      The exception that was thrown.
     * @param headers The HTTP headers for the response.
     * @param status  The HTTP status code for the response.
     * @param request The web request.
     * @return A ResponseEntity containing an error response.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        log.error(ex.getMessage(), ex);

        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getObjectName() + " : " + error.getDefaultMessage())
                .toList();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Validation Errors")
                .statusCode(status.value())
                .status(HttpStatus.valueOf(status.value()))
                .build();

        return ResponseEntity.status(status).body(errorResponse);

    }

    /**
     * Handles the missing servlet request parameter exception, which triggers when there are missing parameters in the request.
     *
     * @param ex      The exception that was thrown.
     * @param headers The HTTP headers for the response.
     * @param status  The HTTP status code for the response.
     * @param request The web request.
     * @return A ResponseEntity containing an error response.
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        log.error(ex.getMessage(), ex);

        List<String> details = new ArrayList<>();
        details.add(ex.getParameterName() + " parameter is missing");

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Validation Errors")
                .statusCode(status.value())
                .status(HttpStatus.valueOf(status.value()))
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }

    /**
     * Handles NotFoundException, returning an ErrorResponse with appropriate status and message.
     *
     * @param exception The exception that was thrown.
     * @return A ResponseEntity containing an error response.
     */
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {

        log.error(exception.getMessage(), exception);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .statusCode(NotFoundException.STATUS.value())
                .status(NotFoundException.STATUS)
                .build();

        return ResponseEntity.status(NotFoundException.STATUS).body(errorResponse);
    }

    /**
     * Handles AlreadyException, returning an ErrorResponse with appropriate status and message.
     *
     * @param exception The exception that was thrown.
     * @return A ResponseEntity containing an error response.
     */
    @ExceptionHandler(AlreadyException.class)
    protected ResponseEntity<Object> handleAlreadyException(AlreadyException exception) {

        log.error(exception.getMessage(), exception);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .statusCode(AlreadyException.STATUS.value())
                .status(AlreadyException.STATUS)
                .build();

        return ResponseEntity.status(AlreadyException.STATUS).body(errorResponse);
    }

    /**
     * Handles ProcessException, returning an ErrorResponse with appropriate status and message.
     *
     * @param exception The exception that was thrown.
     * @return A ResponseEntity containing an error response.
     */
    @ExceptionHandler(ProcessException.class)
    protected ResponseEntity<Object> handleProcessException(ProcessException exception) {

        log.error(exception.getMessage(), exception);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .statusCode(ProcessException.STATUS.value())
                .status(ProcessException.STATUS)
                .build();

        return ResponseEntity.status(ProcessException.STATUS).body(errorResponse);
    }
}
