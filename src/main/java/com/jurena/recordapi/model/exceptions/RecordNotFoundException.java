package com.jurena.recordapi.model.exceptions;

public class RecordNotFoundException extends RecordException{

    public RecordNotFoundException(String recordKey) {
        super("Record with primary key " + recordKey + " not found.");
    }
}
