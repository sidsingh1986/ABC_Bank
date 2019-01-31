package com.abc.bank.abc.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler({ResourceNotFoundException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<ExceptionResponse> resourceNotFound(Exception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(HttpStatus.NOT_FOUND.toString());
        exceptionResponse.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalInputException.class, NullPointerException.class,
            MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponse> illegalArgumentPass(Exception illegalEx) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        exceptionResponse.setErrorMessage(illegalEx.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
