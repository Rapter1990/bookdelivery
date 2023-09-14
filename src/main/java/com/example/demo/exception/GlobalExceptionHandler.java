package com.example.demo.exception;

import com.example.demo.exception.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // handleHttpMediaTypeNotSupported : triggers when the JSON is invalid
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatusCode status,
                                                                     WebRequest request) {

        List<String> details = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        details.add(builder.toString());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Invalid JSON")
                .timestamp(LocalDateTime.now())
                .statusCode(status.value())
                .status(HttpStatus.valueOf(status.value()))
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }

    // handleHttpMessageNotReadable : triggers when the JSON is malformed
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Malformed JSON request")
                .timestamp(LocalDateTime.now())
                .statusCode(status.value())
                .status(HttpStatus.valueOf(status.value()))
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }

    // handleMethodArgumentNotValid : triggers when @Valid fails
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getObjectName() + " : " + error.getDefaultMessage())
                .toList();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Validation Errors")
                .timestamp(LocalDateTime.now())
                .statusCode(status.value())
                .status(HttpStatus.valueOf(status.value()))
                .build();

        return ResponseEntity.status(status).body(errorResponse);

    }

    // handleMissingServletRequestParameter : triggers when there are missing parameters
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> details = new ArrayList<>();
        details.add(ex.getParameterName() + " parameter is missing");

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorDetails(details)
                .message("Validation Errors")
                .timestamp(LocalDateTime.now())
                .statusCode(status.value())
                .status(HttpStatus.valueOf(status.value()))
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }
}
