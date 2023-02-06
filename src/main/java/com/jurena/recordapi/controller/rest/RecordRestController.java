package com.jurena.recordapi.controller.rest;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordRegister;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecordRestController {

    Record getRecord(String recordKey);
    List<RecordRegister> createRecord(MultipartFile records) throws IOException;
    void deleteRecord(String recordKey);

}
