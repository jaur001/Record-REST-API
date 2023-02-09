package com.jurena.recordapi.model.exceptions;

public class MissingRecordKeyException extends RecordException{

    public MissingRecordKeyException() {
        super("Record primary key is missing");
    }
}
