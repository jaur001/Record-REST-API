package com.jurena.recordapi.dao;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordResponse;
import com.jurena.recordapi.respository.RecordRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("csvFileRecordDao")
public class RecordDaoImpl implements RecordDao{

    private final RecordRepository recordRepository;

    public RecordDaoImpl(@Qualifier("csvRecordRepository") RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public List<Record> getRecords(){
        return recordRepository.getRecords();
    }

    @Override
    public Record getRecord(String recordKey){
        return recordRepository.getRecord(recordKey);
    }

    @Override
    public RecordResponse saveRecord(Record record) {
        return recordRepository.saveRecord(record);
    }

    public RecordResponse updateRecord(Record record) {
        return recordRepository.updateRecord(record);
    }

    @Override
    public void deleteRecord(String recordKey) {
        recordRepository.deleteRecord(recordKey);
    }
}
