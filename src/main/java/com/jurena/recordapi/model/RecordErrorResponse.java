package com.jurena.recordapi.model;

import com.jurena.recordapi.model.exceptions.RecordException;

public class RecordErrorResponse extends RecordResponse{

    private final RecordException exception;

    public RecordErrorResponse(RecordException e) {
        super("-1", e.getMessage());
        this.exception = e;
    }

    public RecordException getException() {
        return exception;
    }
}
