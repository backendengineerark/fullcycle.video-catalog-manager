package app.projetaria.videocatalogmanager.api.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import app.projetaria.videocatalogmanager.api.exception.error.ApiError;
import app.projetaria.videocatalogmanager.application.exception.NotFoundException;
import app.projetaria.videocatalogmanager.domain.exception.DomainException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<ApiError> handleDomainException(DomainException exception) {
        return this.buildResponseEntity(ApiError.builder()
            .errorCode(BAD_REQUEST.value())
            .statusCode(BAD_REQUEST)
            .message(exception.getMessage())
            .timestamp(LocalDateTime.now())
            .build());
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException exception) {
        return this.buildResponseEntity(ApiError.builder()
            .errorCode(NOT_FOUND.value())
            .statusCode(NOT_FOUND)
            .message(exception.getMessage())
            .timestamp(LocalDateTime.now())
            .build());
    }

   private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
       return new ResponseEntity<>(apiError, apiError.getStatusCode());
   }
}
