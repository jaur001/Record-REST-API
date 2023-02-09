package com.jurena.recordapi.model;

public class RecordResponse {

    private String recordKey;
    private String message;

    public RecordResponse(String recordKey, String message) {
        this.recordKey = recordKey;
        this.message = message;
    }

    public String getRecordKey() {
        return recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
