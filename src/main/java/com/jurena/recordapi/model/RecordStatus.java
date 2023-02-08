package com.jurena.recordapi.model;

import org.springframework.http.HttpStatus;

public class RecordStatus {

    private String recordKey;
    private String status;
    private String message;

    public RecordStatus(String recordKey, HttpStatus status, String message) {
        this.recordKey = recordKey;
        this.status = status.toString();
        this.message = message;
    }

    public String getRecordKey() {
        return recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
