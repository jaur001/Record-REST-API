package com.jurena.recordapi.model;

public class RecordStatusException extends RuntimeException{


    private final RecordStatus recordStatus;

    public RecordStatusException(RecordStatus recordStatus) {
        super(recordStatus.getMessage());
        this.recordStatus = recordStatus;
    }

    public RecordStatus getRecordStatus() {
        return recordStatus;
    }
}
