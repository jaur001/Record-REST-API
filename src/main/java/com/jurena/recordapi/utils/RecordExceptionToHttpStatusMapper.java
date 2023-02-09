package com.jurena.recordapi.utils;

import com.jurena.recordapi.model.exceptions.*;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public class RecordExceptionToHttpStatusMapper {

    private RecordExceptionToHttpStatusMapper() {
    }

    private static final Map<String, HttpStatus> map;

    static {
        map = new LinkedHashMap<>();
        map.put(RecordNotFoundException.class.getSimpleName(),HttpStatus.NOT_FOUND);
        map.put(MissingRecordKeyException.class.getSimpleName(),HttpStatus.BAD_REQUEST);
        map.put(AlreadyExistRecordKeyException.class.getSimpleName(),HttpStatus.CONFLICT);
        map.put(TimestampFormatException.class.getSimpleName(),HttpStatus.BAD_REQUEST);
    }

    public static HttpStatus getHttpStatus(RecordException e){
        return map.get(e.getClass().getSimpleName());
    }
}
