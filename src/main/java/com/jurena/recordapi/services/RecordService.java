package com.jurena.recordapi.services;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecordService {

    List<Record> getRecords();
    Record getRecord(String recordKey);
    RecordStatus createRecord(Record record);
    RecordStatus updateRecord(Record record);
    List<RecordStatus> createRecords(MultipartFile csv);
    List<RecordStatus> updateRecords(MultipartFile csv);
    RecordStatus deleteRecord(String recordKey);
}
