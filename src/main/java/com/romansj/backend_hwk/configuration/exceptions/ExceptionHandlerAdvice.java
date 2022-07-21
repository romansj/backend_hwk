package com.romansj.backend_hwk.configuration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleNotReadable(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleArgumentException(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Wrong inputs provided");
    }

    /**
     * @param ex Exception which contains a list of constraint violations, which can be returned to client
     * @return Returns {@ResponseEntity} of {@HttpStatus.BAD_REQUEST} with body containing list of errors
     */
    @ExceptionHandler(MyConstraintException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MyConstraintException ex) {
        Map<String, Map<String, String>> body = new HashMap<>();

        // Pair would work, but adds "first" and "second" to json reponse
        // Custom class results in 500 exception unless it is serialized to json, and Map of String-Map is easily extendable with more items
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(constraintViolation -> {
            errors.put(String.valueOf(constraintViolation.path()), constraintViolation.message());
        });

        // can add more items with subitems as needed
        body.put("errors", errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }


}
