package com.jurena.recordapi.utils;

import com.jurena.recordapi.model.RecordErrorResponse;
import com.jurena.recordapi.model.RecordHttpResponse;
import com.jurena.recordapi.model.RecordResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;


public class RecordRestUtils {

    private static final Logger logger = LogManager.getLogger();

    private RecordRestUtils() {
    }

    public static RecordHttpResponse convertToRecordHttpResponse(RecordResponse response){
        if(response instanceof RecordErrorResponse){
            RecordErrorResponse errorResponse = (RecordErrorResponse) response;
            logger.log(Level.ERROR,errorResponse.getMessage());
            HttpStatus httpStatus = RecordExceptionToHttpStatusMapper.getHttpStatus(errorResponse.getException());
            return new RecordHttpResponse(errorResponse.getRecordKey(),errorResponse.getMessage(), httpStatus);
        }
        logger.log(Level.INFO,response.getMessage());
        return new RecordHttpResponse(response.getRecordKey(),response.getMessage(),HttpStatus.OK);
    }
}
