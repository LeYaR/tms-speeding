package com.tms.speeding.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tms.speeding.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        Logger.getLogger(RuntimeException.class.getName()).log(Level.SEVERE, null, ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        Logger.getLogger(CustomException.class.getName()).log(Level.SEVERE, null, ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
