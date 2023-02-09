package com.jurena.recordapi.model.exceptions;

public class AlreadyExistRecordKeyException extends RecordException{

    public AlreadyExistRecordKeyException(String recordKey) {
        super("Primary Key " + recordKey + " already used");
    }
}
