package com.jurena.recordapi.rest;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecordRestController {

    List<Record> getRecords();
    Record getRecord(String recordKey);
    List<RecordStatus> createRecords(MultipartFile csv);
    List<RecordStatus> updateRecords(MultipartFile csv);
    RecordStatus deleteRecord(String recordKey);

}
