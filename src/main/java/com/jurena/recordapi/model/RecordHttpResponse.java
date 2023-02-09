package com.jurena.recordapi.model;

import org.springframework.http.HttpStatus;

public class RecordHttpResponse extends RecordResponse{

    private final String httpStatus;

    public RecordHttpResponse(String recordKey, String message, HttpStatus httpStatus) {
        super(recordKey, message);
        this.httpStatus = httpStatus.toString();
    }

    public String getHttpStatus() {
        return httpStatus;
    }
}
