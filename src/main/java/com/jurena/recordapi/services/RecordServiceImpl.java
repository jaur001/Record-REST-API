package com.jurena.recordapi.services;

import com.jurena.recordapi.dao.RecordDao;
import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordStatus;
import com.jurena.recordapi.utils.RecordCsvParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("springRecordService")
public class RecordServiceImpl implements RecordService{

    private final RecordDao recordDao;

    public RecordServiceImpl(@Qualifier("csvFileRecordDao") RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    @Override
    public List<Record> getRecords() {
        return recordDao.getRecords();
    }

    @Override
    public Record getRecord(String recordKey) {
        return recordDao.getRecord(recordKey);
    }

    @Override
    public RecordStatus createRecord(Record record) {
        return recordDao.createRecord(record);
    }

    @Override
    public RecordStatus updateRecord(Record record) {
        return recordDao.updateRecord(record);
    }

    @Override
    public List<RecordStatus> createRecords(MultipartFile csv) {
        List<Record> records = RecordCsvParser.parseCsv(csv);
        return records.stream()
                .map(this::createRecord)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecordStatus> updateRecords(MultipartFile csv) {
        List<Record> records = RecordCsvParser.parseCsv(csv);
        return records.stream()
                .map(this::updateRecord)
                .collect(Collectors.toList());
    }

    @Override
    public RecordStatus deleteRecord(String recordKey) {
        return recordDao.deleteRecord(recordKey);
    }
}
