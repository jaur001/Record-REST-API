package com.jurena.recordapi.services;

import com.jurena.recordapi.model.Record;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecordService {

    public static List<Record> parseCsv(MultipartFile csv) throws IOException {
        return parseCsvStream(new BufferedReader(new InputStreamReader(csv.getInputStream())).lines());
    }

    public static List<Record> parseCsvStream(Stream<String> stream){
        return stream.skip(1)
                .filter(line -> line.length()>0)
                .map(RecordService::parseCsvLine)
                .collect(Collectors.toList());
    }

    private static Record parseCsvLine(String s) {
        String[] params = s.split(",");
        if(params.length<4 || params[0].length()==0) return new Record();
        return new Record(params[0],params[1],params[2],params[3]);
    }

    public static String stringifyRecord(Record record){
        return record.getPrimaryKey() + "," +
            record.getName() + "," +
            record.getDescription() + "," +
            record.getUpdatedTimeStamp() ;
    }
}
