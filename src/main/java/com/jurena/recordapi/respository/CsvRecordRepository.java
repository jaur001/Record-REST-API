package com.jurena.recordapi.respository;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.RecordStatus;
import com.jurena.recordapi.utils.RecordCsvParser;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

@Component("csvRecordRepository")
@Scope("singleton")
public class CsvRecordRepository implements RecordRepository{


    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
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
        return persistedRecords.stream()
                .filter(record -> record.getPrimaryKey().equals(recordKey))
                .findFirst().orElse(null);
    }


    private List<Record> readRecords() {
        try {
            return RecordCsvParser.parseCsvStream(Files.readAllLines(repositoryPath).stream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public synchronized RecordStatus saveRecord(Record record){
        if(record.getPrimaryKey()==null) return new RecordStatus("-1", HttpStatus.BAD_REQUEST,"Missing Primary Key");
        if(persistedRecords.contains(record))
            return new RecordStatus(record.getPrimaryKey(), HttpStatus.CONFLICT,"Primary Key " + record.getPrimaryKey() + " already exist");
        if(!isCorrectDateFormat(record.getUpdatedTimeStamp()))
            return new RecordStatus(record.getPrimaryKey(), HttpStatus.BAD_REQUEST,"Timestamp format is not correct, the format must be ISO8601.");
        persistedRecords.add(record);
        return new RecordStatus(record.getPrimaryKey(),HttpStatus.OK,"Record " + record.getPrimaryKey() + " created");
    }

    public synchronized RecordStatus updateRecord(Record record){
        if(record.getPrimaryKey()==null) return new RecordStatus("-1", HttpStatus.BAD_REQUEST,"Missing Primary Key");
        Record currentRecord = getRecord(record.getPrimaryKey());
        if(currentRecord == null)
            return new RecordStatus(record.getPrimaryKey(), HttpStatus.NOT_FOUND,"Primary Key " + record.getPrimaryKey() + " does not exist");
        if(!isCorrectDateFormat(record.getUpdatedTimeStamp()))
            return new RecordStatus(record.getPrimaryKey(), HttpStatus.BAD_REQUEST,"Timestamp format is not correct, the format must be ISO8601.");
        persistedRecords.remove(currentRecord);
        persistedRecords.add(record);
        return new RecordStatus(record.getPrimaryKey(),HttpStatus.OK,"Record " + record.getPrimaryKey() + " updated");
    }

    public RecordStatus deleteRecord(String recordKey){
        Record record = getRecord(recordKey);
        if(record == null)
            return new RecordStatus(recordKey, HttpStatus.NOT_FOUND,"Record " + recordKey + " not found");
        persistedRecords.remove(record);
        return new RecordStatus(record.getPrimaryKey(),HttpStatus.OK,"Record " + recordKey + " successfully deleted");
    }

    private void writeRecords(List<Record> records){
        try {
            String csv = headers + RecordCsvParser.stringifyRecords(records) + "\n";
            Files.write(repositoryPath,csv.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isCorrectDateFormat(String date){
        if(date.trim().length()==0) return true;
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setLenient(false);
        try {
            df.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @PreDestroy
    public void persistRecords(){
        writeRecords(persistedRecords);
    }

}
