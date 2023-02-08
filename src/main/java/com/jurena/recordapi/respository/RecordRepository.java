package com.jurena.recordapi.respository;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordStatus;

import java.util.List;

public interface RecordRepository {

    List<Record> getRecords();
    Record getRecord(String recordKey);
    RecordStatus saveRecord(Record record);
    RecordStatus updateRecord(Record record);
    RecordStatus deleteRecord(String recordKey);
}
