package com.jurena.recordapi.dao;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordNotFoundException;
import com.jurena.recordapi.model.RecordRegister;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("csvFileRecordDao")
@Scope("singleton")
public class CsvFileRecordDao extends CsvFileHandler implements RecordDao{

    public CsvFileRecordDao(@Value("${persistedRecords.path}")String path) {
        super(path);
    }

    @Override
    protected void closeFile() {

        super.closeFile();
    }

    @PreDestroy


    @Override
    public Record getRecord(String recordKey) throws RecordNotFoundException {
        return persistedRecords.stream()
                .filter(record -> record.getPrimaryKey().equals(recordKey))
                .findFirst().orElseThrow(() -> new RecordNotFoundException(recordKey));
    }

    @Override
    public RecordRegister createRecord(Record record) {
        if(record.getPrimaryKey()==null) return new RecordRegister("-1", HttpStatus.BAD_REQUEST,"Missing Primary Key");
        if(persistedRecords.contains(record))
            return new RecordRegister("-1", HttpStatus.CONFLICT,"Primary Key" + record.getPrimaryKey() + " already exist");
        try {
            writeRecord(record);
            persistedRecords.add(record);
            return new RecordRegister(record.getPrimaryKey(),HttpStatus.MULTI_STATUS,"Record " + record.getPrimaryKey() + " created");
        } catch (IOException e) {
            return new RecordRegister(record.getPrimaryKey(),HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @Override
    public boolean deleteRecord(Record record) {
        return false;
    }
}
