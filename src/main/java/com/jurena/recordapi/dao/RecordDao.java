package com.jurena.recordapi.dao;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordNotFoundException;
import com.jurena.recordapi.model.RecordRegister;


public interface RecordDao {
    Record getRecord(String recordKey) throws RecordNotFoundException;
    RecordRegister createRecord(Record record);
    boolean deleteRecord(Record record);
}
