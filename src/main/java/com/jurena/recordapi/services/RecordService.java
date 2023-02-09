package com.jurena.recordapi.services;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecordService {

    List<Record> getRecords();
    Record getRecord(String recordKey);
    RecordResponse saveRecord(Record record);
    RecordResponse updateRecord(Record record);
    List<RecordResponse> saveRecords(MultipartFile csv);
    List<RecordResponse> updateRecords(MultipartFile csv);
    void deleteRecord(String recordKey);
}
