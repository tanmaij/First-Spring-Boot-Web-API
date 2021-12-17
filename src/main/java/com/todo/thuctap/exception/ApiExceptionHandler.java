package com.todo.thuctap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse hanlderNotFoundException(NotFoundException ex, WebRequest wb){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),ex.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse hanlderCustomException(CustomException ex, WebRequest wb){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse hanlderCustomException(AuthorizationException ex, WebRequest wb){
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),ex.getMessage());
    }

}
