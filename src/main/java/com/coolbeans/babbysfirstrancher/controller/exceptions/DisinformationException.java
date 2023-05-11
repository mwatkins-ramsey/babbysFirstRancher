package com.coolbeans.babbysfirstrancher.controller.exceptions;

import com.coolbeans.babbysfirstrancher.controller.MessageController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class DisinformationException extends ArithmeticException{
    public DisinformationException(){
        super("Some or all of the content in this message is disputed and may be misleading.");
    }
    public DisinformationException(String msg){
        super(msg);
    }
}
