package pro.gravit.simplecabinet.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pro.gravit.simplecabinet.web.exception.EntityNotFoundException;

@ControllerAdvice
public class CustomRestExceptionHandler {
    public record ApiError(String error) {
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleException(EntityNotFoundException e) {
        ApiError error = new ApiError(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
