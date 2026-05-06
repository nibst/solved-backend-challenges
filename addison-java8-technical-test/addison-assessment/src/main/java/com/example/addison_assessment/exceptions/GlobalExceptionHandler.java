package com.example.addison_assessment.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.concurrent.CompletionException;

/**
 * Maps domain exceptions to appropriate HTTP responses.
 *
 * <p>Spring unwraps {@link CompletionException} automatically when a controller
 * returns a {@link java.util.concurrent.CompletableFuture}, so only the cause
 * needs to be handled here.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Object> handleAuthException(AuthException ex) {
        Map<String, String> body = Map.of("error", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }


   @ExceptionHandler(UserTokenException.class)
    public ResponseEntity<Object> handleUserTokenException(UserTokenException ex){
        Map<String, String> body = Map.of("error", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        Map<String, String> body = Map.of("error", "An unexpected server error occurred." + ex.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
