package com.jurena.recordapi.dao;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordStatus;

import java.util.List;


public interface RecordDao {

    List<Record> getRecords();
    Record getRecord(String recordKey);
    RecordStatus createRecord(Record record);
    RecordStatus updateRecord(Record record);
    RecordStatus deleteRecord(String recordKey);
}
