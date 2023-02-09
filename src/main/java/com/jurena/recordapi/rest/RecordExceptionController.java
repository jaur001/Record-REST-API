package com.jurena.recordapi.rest;

import com.jurena.recordapi.model.exceptions.RecordException;
import com.jurena.recordapi.utils.RecordExceptionToHttpStatusMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RecordExceptionController {


    @ExceptionHandler(value = RecordException.class)
    public ResponseEntity<Object> exception(RecordException e) {
        HttpStatus httpStatus = RecordExceptionToHttpStatusMapper.getHttpStatus(e);
        return new ResponseEntity<>(e.getMessage(), httpStatus);
    }
}
