package com.jurena.recordapi.model.exceptions;

public class TimestampFormatException extends RecordException{

    public TimestampFormatException() {
        super("Timestamp format is not correct, the format must be ISO8601.");
    }
}
