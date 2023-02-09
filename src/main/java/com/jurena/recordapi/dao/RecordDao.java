package com.jurena.recordapi.dao;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordResponse;

import java.util.List;


public interface RecordDao {

    List<Record> getRecords();
    Record getRecord(String recordKey);
    RecordResponse saveRecord(Record record);
    RecordResponse updateRecord(Record record);
    void deleteRecord(String recordKey);
}
