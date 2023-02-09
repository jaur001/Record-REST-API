package com.jurena.recordapi.rest;

import com.jurena.recordapi.model.*;
import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.services.RecordService;
import com.jurena.recordapi.utils.RecordRestUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

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
        return recordService.getRecord(recordKey);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.MULTI_STATUS)
    public List<RecordHttpResponse> saveRecords(@RequestParam("csv") MultipartFile csv){
        List<RecordResponse> responses = recordService.saveRecords(csv);
        return responses.stream()
                .map(RecordRestUtils::convertToRecordHttpResponse)
                .collect(Collectors.toList());
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.MULTI_STATUS)
    public List<RecordHttpResponse> updateRecords(@RequestParam("csv") MultipartFile csv){
        List<RecordResponse> responses = recordService.updateRecords(csv);
        return responses.stream()
                .map(RecordRestUtils::convertToRecordHttpResponse)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{recordKey}")
    public void deleteRecord(@PathVariable String recordKey) {
        recordService.deleteRecord(recordKey);
    }
}
