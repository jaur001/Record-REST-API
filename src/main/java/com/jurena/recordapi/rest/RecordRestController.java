package com.jurena.recordapi.rest;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordHttpResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecordRestController {

    List<Record> getRecords();
    Record getRecord(String recordKey);
    List<RecordHttpResponse> saveRecords(MultipartFile csv);
    List<RecordHttpResponse> updateRecords(MultipartFile csv);
    void deleteRecord(String recordKey);

}
