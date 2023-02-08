package com.jurena.recordapi.rest;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordStatusException;
import com.jurena.recordapi.model.RecordNotFoundException;
import com.jurena.recordapi.model.RecordStatus;
import com.jurena.recordapi.services.RecordService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class RecordRestControllerImpl implements RecordRestController{

    private final RecordService recordService;

    public RecordRestControllerImpl(@Qualifier("springRecordService") RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping()
    public List<Record> getRecords(){
        return recordService.getRecords();
    }

    @GetMapping("/{recordKey}")
    public Record getRecord(@PathVariable String recordKey) {
        Record record = recordService.getRecord(recordKey);
        if(record == null)
            throw new RecordNotFoundException(recordKey);
        return record;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.MULTI_STATUS)
    public List<RecordStatus> createRecords(@RequestParam("csv") MultipartFile csv){
        return recordService.createRecords(csv);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.MULTI_STATUS)
    public List<RecordStatus> updateRecords(@RequestParam("csv") MultipartFile csv){
        return recordService.updateRecords(csv);
    }

    @DeleteMapping("/{recordKey}")
    public RecordStatus deleteRecord(@PathVariable String recordKey) {
        RecordStatus status = recordService.deleteRecord(recordKey);
        if(status.getStatusCode().isError())
            throw new RecordStatusException(status);
        return status;
    }
}
