package com.programmingtechie.productservice.exception;

import com.example.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


@RestControllerAdvice
@Order(Integer.MAX_VALUE)
@Slf4j
public class ExceptionControllerGenericError {
    private static final String INVALID_INPUT_CLIENT_4XX = "Invalid input from client 4xx";
    private static final String GENERAL_ERROR_SERVER_5XX = "General error server 5xx";

    public ErrorResponse toErrorResponse(String name, String message, HttpServletRequest req) {
        String error = UUID.randomUUID().toString();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setName(name);
        errorResponse.setMessage(message);
        errorResponse.setErrorId(error);
        log.warn("Error tracking Id {}", error);
        return errorResponse;
    }

    private ResponseEntity<ErrorResponse> toErrorResponse(HttpStatus status, String name, String message,
                                                          HttpServletRequest req) {
        return ResponseEntity.status(status).body(toErrorResponse(name, message, req));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleUncheckedException(RuntimeException ex, HttpServletRequest req) {
        return toErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                GENERAL_ERROR_SERVER_5XX,
                ex.getLocalizedMessage(),
                req
        );
    }

    @ExceptionHandler({MissingServletRequestParameterException.class,
            IllegalArgumentException.class, MissingRequestHeaderException.class,
            MissingRequestValueException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleCheckedException(Exception ex, HttpServletRequest req) {
        return toErrorResponse(
                HttpStatus.BAD_REQUEST,
                INVALID_INPUT_CLIENT_4XX,
                ex.getLocalizedMessage(),
                req
        );
    }
}