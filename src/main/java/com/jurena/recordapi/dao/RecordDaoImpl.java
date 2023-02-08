package com.jurena.recordapi.dao;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordStatus;
import com.jurena.recordapi.respository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public RecordStatus createRecord(Record record) {
        return recordRepository.saveRecord(record);
    }

    public RecordStatus updateRecord(Record record) {
        return recordRepository.updateRecord(record);
    }

    @Override
    public RecordStatus deleteRecord(String recordKey) {
        return recordRepository.deleteRecord(recordKey);
    }
}
