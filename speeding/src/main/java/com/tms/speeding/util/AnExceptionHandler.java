package com.tms.speeding.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AnExceptionHandler {
    @ExceptionHandler({RuntimeException.class})
    public ResponseObject handleRuntimeException(RuntimeException ex) {
        Logger.getLogger(RuntimeException.class.getName()).log(Level.SEVERE, null, ex);
        return new ResponseObject(false, "error", null, ex.getMessage());
    }
}
