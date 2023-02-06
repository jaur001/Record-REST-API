package com.jurena.recordapi.controller.rest;

import com.jurena.recordapi.dao.RecordDao;
import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordNotFoundException;
import com.jurena.recordapi.model.RecordRegister;
import com.jurena.recordapi.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/records")
public class SpringRecordRestController implements RecordRestController{

    @Autowired
    @Qualifier("csvFileRecordDao")
    private RecordDao recordDao;

    @GetMapping("/{recordKey}")
    public Record getRecord(@PathVariable String recordKey) {
        return recordDao.getRecord(recordKey);
    }

    @PostMapping()
    public List<RecordRegister> createRecord(@RequestParam("csv") MultipartFile csv) throws IOException {
        List<Record> records = RecordService.parseCsv(csv);
        return records.stream()
                .map(record -> recordDao.createRecord(record))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{recordKey}")
    public void deleteRecord(String recordKey) {

    }
}
