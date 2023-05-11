package com.coolbeans.babbysfirstrancher.controller;

import com.coolbeans.babbysfirstrancher.controller.exceptions.DisinformationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {MessageController.class})
public class MessageControllerAdvice {
    @ExceptionHandler(DisinformationException.class)
    public ResponseEntity<String> handleDisiformationException(DisinformationException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
}
