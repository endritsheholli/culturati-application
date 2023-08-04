package com.iotiq.application.exception;

import com.iotiq.commons.exceptions.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class GeoJsonFileOperationException extends ApplicationException {

    private final transient Logger log = LoggerFactory.getLogger(TenantDataSourceConnectionException.class);

    public GeoJsonFileOperationException(String prefix, String message, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, prefix, Collections.singletonList(message));
        
        log.error(prefix , cause);
    }
}
