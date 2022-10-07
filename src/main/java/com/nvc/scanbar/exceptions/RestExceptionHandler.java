package com.nvc.scanbar.exceptions;

import com.nvc.scanbar.beans.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorModel> handleEntityNotFound(NotFoundException ex){
        ErrorModel error = new ErrorModel(HttpStatus.NOT_FOUND,  ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DataConflictException.class)
    private ResponseEntity<ErrorModel> handleDataConflictException (DataConflictException ex){
        ErrorModel error = new ErrorModel(HttpStatus.CONFLICT, ex);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(InternalServerError.class)
    private ResponseEntity<ErrorModel> handleInternalServerException (InternalServerError ex){
        ErrorModel error = new ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", ex);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(AuthException.class)
    private ResponseEntity<ErrorModel> handleAuthException (AuthException ex){
        ErrorModel error = new ErrorModel(HttpStatus.UNAUTHORIZED, ex);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<ErrorModel> handleAuthException (ValidationException ex){
        ErrorModel error = new ErrorModel(HttpStatus.PRECONDITION_FAILED, ex);
        return new ResponseEntity<>(error, HttpStatus.PRECONDITION_FAILED);
    }
}
