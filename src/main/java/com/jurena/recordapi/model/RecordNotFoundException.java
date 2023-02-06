package com.jurena.recordapi.model;

public class RecordNotFoundException extends RuntimeException{

    public RecordNotFoundException(String recordKey) {
        super("Record " + recordKey + " not found.");
    }
}
