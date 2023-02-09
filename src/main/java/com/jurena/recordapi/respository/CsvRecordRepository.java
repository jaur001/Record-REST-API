package com.jurena.recordapi.respository;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordErrorResponse;
import com.jurena.recordapi.model.RecordResponse;
import com.jurena.recordapi.model.exceptions.*;
import com.jurena.recordapi.utils.RecordCsvParser;
import jakarta.annotation.PreDestroy;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Component("csvRecordRepository")
@Scope("singleton")
public class CsvRecordRepository implements RecordRepository{

    private static final Logger logger = LogManager.getLogger();
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private final String headers;
    private final Path repositoryPath;
    private final List<Record> persistedRecords;

    public CsvRecordRepository(@Value("${persistedRecords.path}")String path, @Value("${persistedRecords.headers}") String headers) {
        this.headers = headers;
        repositoryPath = Paths.get(path);
        persistedRecords = readRecords();
    }

    public List<Record> getRecords() {
        return persistedRecords;
    }

    @Override
    public Record getRecord(String recordKey) {
        Record record = findRecord(recordKey);
        if(record == null)
            throw new RecordNotFoundException(recordKey);
        return record;
    }

    public Record findRecord(String recordKey) {
        return persistedRecords.stream()
                .filter(record -> record.getPrimaryKey().equals(recordKey))
                .findFirst().orElse(null);
    }


    private List<Record> readRecords() {
        try {
            logger.log(Level.INFO,"Reading records");
            List<Record> records = RecordCsvParser.parseCsvStream(Files.readAllLines(repositoryPath).stream());
            logger.log(Level.INFO,"Records retrieved");
            return records;
        } catch (IOException e) {
            throw new RecordException(e.getMessage());
        }
    }

    public synchronized RecordResponse saveRecord(Record record){
        if(record.getPrimaryKey()==null) return new RecordErrorResponse(new MissingRecordKeyException());
        if(persistedRecords.contains(record))
            return new RecordErrorResponse(new AlreadyExistRecordKeyException(record.getPrimaryKey()));
        if(isNotCorrectDateFormat(record.getUpdatedTimeStamp()))
            return new RecordErrorResponse(new TimestampFormatException());
        persistedRecords.add(record);
        return new RecordResponse(record.getPrimaryKey(),"Record " + record.getPrimaryKey() + " created");
    }

    public synchronized RecordResponse updateRecord(Record record){
        if(record.getPrimaryKey()==null) return new RecordErrorResponse(new MissingRecordKeyException());
        Record currentRecord = findRecord(record.getPrimaryKey());
        if(currentRecord == null)
            return new RecordErrorResponse(new RecordNotFoundException(record.getPrimaryKey()));
        if(isNotCorrectDateFormat(record.getUpdatedTimeStamp()))
            return new RecordErrorResponse(new TimestampFormatException());
        persistedRecords.remove(currentRecord);
        persistedRecords.add(record);
        return new RecordResponse(record.getPrimaryKey(),"Record " + record.getPrimaryKey() + " updated");
    }

    public void deleteRecord(String recordKey){
        Record record = findRecord(recordKey);
        if(record == null)
            throw new RecordNotFoundException(recordKey);
        persistedRecords.remove(record);
    }

    private void writeRecords(List<Record> records){
        try {
            logger.log(Level.INFO,"Storing records");
            String csv = headers + RecordCsvParser.stringifyRecords(records) + "\n";
            Files.write(repositoryPath,csv.getBytes());
            logger.log(Level.INFO,"Records stored successfully");
        } catch (IOException e) {
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    private static boolean isNotCorrectDateFormat(String date){
        if(date.trim().length()==0) return false;
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setLenient(false);
        try {
            df.parse(date);
        } catch (ParseException e) {
            return true;
        }
        return false;
    }

    @PreDestroy
    public void persistRecords(){
        writeRecords(persistedRecords);
    }

}
