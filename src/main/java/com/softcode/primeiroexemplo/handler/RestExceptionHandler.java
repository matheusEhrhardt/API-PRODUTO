package com.softcode.primeiroexemplo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.softcode.primeiroexemplo.model.error.ErroMessage;
import com.softcode.primeiroexemplo.model.exception.ResourseNotFoundException;

@ControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<?> handleResourseNotFoudException(ResourseNotFoundException ex){

        ErroMessage error = new ErroMessage("Not Found", HttpStatus.NOT_FOUND.value(),ex.getMessage() );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
