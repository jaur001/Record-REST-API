package com.jurena.recordapi.dao;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.services.RecordService;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class CsvFileHandler {

    protected List<Record> persistedRecords;
    protected FileWriter writer;

    public CsvFileHandler(String path) {
        persistedRecords = readRecords(path);
        writer = getWriter(path);
    }

    protected FileWriter getWriter(String path){
        try {
            return new FileWriter(path,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected List<Record> readRecords(String path) {
        try {
            return RecordService.parseCsvStream(Files.readAllLines(Paths.get(path)).stream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    protected synchronized void writeRecord(Record record) throws IOException {
        writer.write("\n"+RecordService.stringifyRecord(record));
    }

    protected void closeFile(){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
