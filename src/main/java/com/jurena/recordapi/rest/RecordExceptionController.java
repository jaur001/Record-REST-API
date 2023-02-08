package com.jurena.recordapi.rest;

import com.jurena.recordapi.model.RecordStatusException;
import com.jurena.recordapi.model.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RecordExceptionController {

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<Object> exception(RecordNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RecordStatusException.class)
    public ResponseEntity<Object> exception(RecordStatusException e) {
        return new ResponseEntity<>(e.getMessage(), e.getRecordStatus().getStatusCode());
    }
}
