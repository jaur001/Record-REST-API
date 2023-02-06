package com.jurena.recordapi.dao;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordNotFoundException;
import com.jurena.recordapi.model.RecordRegister;
import com.jurena.recordapi.model.RecordStatus;
import com.jurena.recordapi.services.RecordService;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Component("csvFileRecordDao")
@Scope("singleton")
public class CsvFileRecordDao implements RecordDao{

    private List<Record> persistedRecords;
    private FileWriter writer;

    public CsvFileRecordDao(@Value("${persistedRecords.path}") String path) {
        persistedRecords = readRecords(path);
        writer = getWriter(path);
    }

    private FileWriter getWriter(String path){
        try {
            return new FileWriter(path,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Record> readRecords(String path) {
        try {
            return RecordService.parseCsvStream(Files.readAllLines(Paths.get(path)).stream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private synchronized void writeRecord(Record record) throws IOException {
        writer.write("\n"+RecordService.stringifyRecord(record));
    }

    @Override
    public Record getRecord(String recordKey) throws RecordNotFoundException {
        return persistedRecords.stream()
                .filter(record -> record.getPrimaryKey().equals(recordKey))
                .findFirst().orElseThrow(() -> new RecordNotFoundException(recordKey));
    }

    @Override
    public RecordRegister createRecord(Record record) {
        if(record.getPrimaryKey()==null) return new RecordRegister("-1",RecordStatus.ERROR,"Missing Primary Key");
        if(persistedRecords.contains(record))
            return new RecordRegister("-1", RecordStatus.ERROR, "Primary Key" + record.getPrimaryKey() + " already exist");
        try {
            writeRecord(record);
            persistedRecords.add(record);
            return new RecordRegister(record.getPrimaryKey(),RecordStatus.SUCCESS,"Record " + record.getPrimaryKey() + " created");
        } catch (IOException e) {
            return new RecordRegister(record.getPrimaryKey(),RecordStatus.ERROR,e.getMessage());
        }
    }

    @Override
    public boolean deleteRecord(Record record) {
        return false;
    }

    @PreDestroy
    private void closeFile(){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
