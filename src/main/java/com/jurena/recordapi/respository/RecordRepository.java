package com.jurena.recordapi.respository;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordResponse;

import java.util.List;

public interface RecordRepository {

    List<Record> getRecords();
    Record getRecord(String recordKey);
    RecordResponse saveRecord(Record record);
    RecordResponse updateRecord(Record record);
    void deleteRecord(String recordKey);
}
