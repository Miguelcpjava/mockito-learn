package dev.oxelab.apitest.resources.exceptions;

import dev.oxelab.apitest.services.exceptions.DataIntegratyViolationException;
import dev.oxelab.apitest.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

@ExceptionHandler(ObjectNotFoundException.class)
public ResponseEntity<StandardError>objectNotFound(ObjectNotFoundException exception, HttpServletRequest request){
    StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),exception.getMessage(),request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
}

    @ExceptionHandler(DataIntegratyViolationException.class)
    public ResponseEntity<StandardError>dataIntegratyViolationException(DataIntegratyViolationException exception, HttpServletRequest request){
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),exception.getMessage(),request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
