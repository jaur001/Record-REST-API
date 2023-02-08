package com.jurena.recordapi.model;

import org.springframework.http.HttpStatus;

public class RecordStatus {

    private String recordKey;
    private HttpStatus statusCode;
    private String message;

    public RecordStatus(String recordKey, HttpStatus statusCode, String message) {
        this.recordKey = recordKey;
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getRecordKey() {
        return recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
