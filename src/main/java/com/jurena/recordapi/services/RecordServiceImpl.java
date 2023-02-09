package com.jurena.recordapi.services;

import com.jurena.recordapi.dao.RecordDao;
import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordResponse;
import com.jurena.recordapi.utils.RecordCsvParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
    public RecordResponse saveRecord(Record record) {
        return recordDao.saveRecord(record);
    }

    @Override
    public RecordResponse updateRecord(Record record) {
        return recordDao.updateRecord(record);
    }

    @Override
    public List<RecordResponse> saveRecords(MultipartFile csv) {
        List<Record> records = RecordCsvParser.parseCsv(csv);
        return records.stream()
                .map(this::saveRecord)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecordResponse> updateRecords(MultipartFile csv) {
        List<Record> records = RecordCsvParser.parseCsv(csv);
        return records.stream()
                .map(this::updateRecord)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRecord(String recordKey) {
        recordDao.deleteRecord(recordKey);
    }
}
