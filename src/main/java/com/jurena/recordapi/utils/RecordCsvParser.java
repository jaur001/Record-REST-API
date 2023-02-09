package com.jurena.recordapi.utils;

import com.jurena.recordapi.model.Record;
import com.jurena.recordapi.model.exceptions.RecordException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecordCsvParser {

    private static final Logger logger = LogManager.getLogger();

    private RecordCsvParser(){}

    public static List<Record> parseCsv(MultipartFile csv){
        logger.log(Level.INFO,"parsing csv");
        try {
            List<Record> records = parseCsvStream(new BufferedReader(new InputStreamReader(csv.getInputStream())).lines());
            logger.log(Level.INFO,"CSV parsed into records");
            return records;
        } catch (IOException e) {
            throw new RecordException(e.getMessage());
        }
    }

    public static List<Record> parseCsvStream(Stream<String> stream){
        return stream.skip(1)
                .filter(line -> line.length()>0)
                .map(RecordCsvParser::parseCsvLine)
                .collect(Collectors.toList());
    }

    private static Record parseCsvLine(String s) {
        String[] params = s.split(",");
        if(params.length<4){
            if(params.length<3) return new Record();
            return new Record(params[0],params[1],params[2],"");
        }
        if(params[0].length()==0) return new Record();
        return new Record(params[0],params[1],params[2],params[3]);
    }

    public static String stringifyRecord(Record record){
        return record.getPrimaryKey() + "," +
            record.getName() + "," +
            record.getDescription() + "," +
            record.getUpdatedTimeStamp() ;
    }

    public static String stringifyRecords(List<Record> records){
        return records.stream()
                .map(RecordCsvParser::stringifyRecord)
                .reduce("",(line1,line2) -> line1 + "\n" + line2);
    }
}
